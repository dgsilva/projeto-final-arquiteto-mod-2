package br.com.cotiinformatica.api.cliente.infrastructure.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.lang.reflect.Field;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.api.cliente.application.dto.request.ClienteMessageDto;
import br.com.cotiinformatica.api.cliente.application.dto.request.ClienteRequestDTO;
import br.com.cotiinformatica.api.cliente.application.dto.response.ClienteResponseDTO;
import br.com.cotiinformatica.api.cliente.domain.collections.LogClientes;
import br.com.cotiinformatica.api.cliente.domain.entities.Cliente;
import br.com.cotiinformatica.api.cliente.domain.entities.Endereco;
import br.com.cotiinformatica.api.cliente.infrastructure.producers.ClienteMessageProducer;
import br.com.cotiinformatica.api.cliente.infrastructure.repositories.ClienteMongoRepository;
import br.com.cotiinformatica.api.cliente.infrastructure.repositories.ClienteRepository;
import br.com.cotiinformatica.api.cliente.reports.ClienteReports;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ClienteMongoRepository clienteMongoRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ClienteMessageProducer clienteMessageProducer;
	
	@Autowired
	ClienteReports clienteReports;

	public ResponseEntity<Cliente> create(ClienteRequestDTO dto) {
		Cliente cliente = new Cliente();

		if (clienteRepository.findByEmail(dto.getEmail()).isPresent()) {
			log.info("Email, já está cadatrado, tente outro");
			throw new IllegalArgumentException("Email, já está cadatrado, tente outro");
		}

		if (clienteRepository.findByCpf(dto.getCpf()).isPresent()) {
			log.info("CPF, já está cadatrado, tente outro");
			throw new IllegalArgumentException("CPF, já está cadatrado, tente outro");
		}

		try {
			cliente = modelMapper.map(dto, Cliente.class);
			cliente.setIdCliente(UUID.randomUUID());

			for (Endereco endereco : dto.getEnderecos()) {
				endereco.setIdEndereco(UUID.randomUUID());
				endereco.setCliente(cliente);
			}
			clienteRepository.save(cliente);
			createWelcomeMessage(cliente);

			LogClientes logClientes = new LogClientes();
			logClientes.setId(UUID.randomUUID());
			logClientes.setDataHora(Instant.now());
			logClientes.setOperacao("CADASTRO");
			logClientes.setDescriao("Cliente cadastrado: " + dto.getNome());
			clienteMongoRepository.save(logClientes);

			log.info("Os dados de cliente foram cadastrado com sucesso!!!");
			return ResponseEntity.status(HttpStatus.CREATED).body(cliente);

		} catch (Exception e) {

			log.error("Erro ao cadastrar o cliente" + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cliente);
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public ResponseEntity<Cliente> atualizar(ClienteRequestDTO dto, UUID idCliente) {
		Cliente cliente = new Cliente();
		try {

			if (!clienteRepository.existsById(idCliente)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			cliente = modelMapper.map(dto, Cliente.class);
			if (cliente == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			// Obtenha o cliente existente do banco de dados
			Cliente clienteExistente = clienteRepository.findById(idCliente).orElse(null);

			cliente.setIdCliente(idCliente);
			for (Endereco endereco : cliente.getEnderecos()) {
				endereco.setCliente(clienteExistente);
			}
			clienteRepository.save(cliente);

			LogClientes logClientes = new LogClientes();
			logClientes.setId(UUID.randomUUID());
			logClientes.setDataHora(Instant.now());
			logClientes.setOperacao("ALTERAÇÃO");
			logClientes.setDescriao("Cliente alterado com sucesso: " + cliente.getNome());
			clienteMongoRepository.save(logClientes);

			log.info("Cliente alterado com sucesso");
			return ResponseEntity.status(HttpStatus.OK).body(cliente);

		} catch (Exception e) {
			log.error("Erro ao realizar alteração do cliente");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cliente);
		}
	}

	public Cliente excluir(UUID idCliente) {
		Optional<Cliente> clienteExcluir = clienteRepository.findById(idCliente);

		if (clienteExcluir.isPresent()) {
			clienteRepository.delete(clienteExcluir.get());

			LogClientes logClientes = new LogClientes();
			logClientes.setId(UUID.randomUUID());
			logClientes.setDataHora(Instant.now());
			logClientes.setOperacao("EXCLUSÃO");
			logClientes.setDescriao("Cliente excluido com sucesso: " + idCliente);
			clienteMongoRepository.save(logClientes);

			log.info("Cliente excluido com sucesso");
		} else {
			log.error("Cliente não encontrado");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
		}

		return clienteExcluir.get();

	}

	public ResponseEntity<Cliente> findById(UUID idCliente) {
		Optional<Cliente> findCliente = clienteRepository.findById(idCliente);
		if (findCliente.isPresent()) {
			return ResponseEntity.ok(findCliente.get());
		}
		log.error("Cliente não encontrado");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	
	public ResponseEntity<Cliente> findByNome(String nome) {
		Optional<Cliente> findCliente = clienteRepository.findByNome(nome);
		if (findCliente.isPresent()) {
			return ResponseEntity.ok(findCliente.get());
		}
		log.error("Cliente não encontrado");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	
	public List<Cliente> findByNomeContaining(String nome) {
		return clienteRepository.findByNomeContaining(nome);
	}
	
	public ResponseEntity<ClienteResponseDTO>uploadFotos(UUID idCliente, MultipartFile imagem){
		
		String tipo = imagem.getContentType();
		if(tipo.equals("image/jpeg") || tipo.equals("image/jpg") || tipo.equals("image/png")) {
			
			byte[]dados = null;
			
			try {
				dados = imagem.getBytes();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			Cliente cliente = upload(idCliente, dados);
			ClienteResponseDTO response = modelMapper.map(cliente, ClienteResponseDTO.class);
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
	}
	
	
	public ResponseEntity<ClienteResponseDTO> atualizarFotoCliente(UUID idCliente,MultipartFile imagem) {
	    if (imagem == null || imagem.isEmpty()) {
	        return ResponseEntity.badRequest().body(null);
	    }
	    
	    String tipo = imagem.getContentType();
	    if (tipo == null || (!tipo.equals("image/jpeg") && !tipo.equals("image/jpg") && !tipo.equals("image/png"))) {
	        return ResponseEntity.badRequest().body(null);
	    }

	    try {
	        byte[] dados = imagem.getBytes();
	        Cliente cliente = upload(idCliente, dados);
	        ClienteResponseDTO response = modelMapper.map(cliente, ClienteResponseDTO.class);
	        return ResponseEntity.ok().body(response);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	public Cliente upload(UUID id, byte[] foto) {
		Optional<Cliente> optional = clienteRepository.findById(id);
		
		if (optional.isEmpty()) {
			throw new IllegalArgumentException("Cliente Invalido.");
		}
		
		Cliente cliente = optional.get();
		cliente.setFoto(foto);
		clienteRepository.save(cliente);
		return cliente;
	}
	
	private void createWelcomeMessage(Cliente cliente) {

		ClienteMessageDto dto = new ClienteMessageDto();
		dto.setEmailTo(cliente.getEmail());
		dto.setSubject("Conta criada com sucesso - API de cliente.");

		StringBuilder sb = new StringBuilder();
		sb.append("<div>");
		sb.append("<p>Parabéns " + cliente.getNome() + ", sua conta de usuário foi criada com sucesso</p>");
		sb.append("<p>Att,</p>");
		sb.append("<p>Equipe COTI Informática,</p>");
		sb.append("</div>");

		dto.setBody(sb.toString());

		try {
			String message = objectMapper.writeValueAsString(dto);
			clienteMessageProducer.sendMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public byte[] getReport()throws Exception{
		//Consultando todos os clientes do banco de dados
		List<Cliente> clientes = clienteRepository.findAll();
		
		//gerando o relatorio em PDF
		ByteArrayInputStream stream = clienteReports.createPdf(clientes);
		
		
		//Retornando o relatorio em bytes
		return stream.readAllBytes();
	}
	
	
	 public ResponseEntity<Cliente>atualizarParcial(UUID idCliente, Map<String,Object>campos){
		Cliente clienteAtual = clienteRepository.findById(idCliente).orElse(null);
		
		if(clienteAtual == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		merge(campos, clienteAtual);
		
		try {
	        clienteRepository.save(clienteAtual);
	        
	        LogClientes logClientes = new LogClientes();
	        logClientes.setId(UUID.randomUUID());
	        logClientes.setDataHora(Instant.now());
	        logClientes.setOperacao("ALTERAÇÃO PARCIAL");
	        logClientes.setDescriao("Cliente alterado parcialmente com sucesso: " + clienteAtual.getNome());
	        clienteMongoRepository.save(logClientes);
	        
	        log.info("Cliente alterado parcialmente com sucesso");
	        return ResponseEntity.status(HttpStatus.OK).body(clienteAtual);
	    } catch (Exception e) {
	        log.error("Erro ao realizar alteração parcial do cliente");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(clienteAtual);
	    }
	}
	
	private void merge(Map<String, Object> dadosOrigem, Cliente cliente) {
		ObjectMapper objectMapper = new ObjectMapper();
		Cliente clienteOrigem = objectMapper.convertValue(dadosOrigem, Cliente.class);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			try {
				Field field = cliente.getClass().getDeclaredField(nomePropriedade);
				field.setAccessible(true);
				Object novoValor = ReflectionUtils.getField(field, clienteOrigem);
				ReflectionUtils.setField(field, cliente, novoValor);
			} catch (NoSuchFieldException e) {
				log.error("Campo inválido: " + nomePropriedade);
			}
		});
	}
	
}
