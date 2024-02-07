package br.com.cotiinformatica.api.cliente.infrastructure.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.api.cliente.application.dto.request.ClienteMessageDto;
import br.com.cotiinformatica.api.cliente.infrastructure.components.MailSenderComponent;

@Service
public class ClienteMessageConsumer {
	@Autowired
	MailSenderComponent mailSenderComponent;
	
	@Autowired
	ObjectMapper objectMapper;
	
	/*
	 * Método para ler os itens da fila
	 */
	@RabbitListener(queues = { "${queue.name}" })
	public void receive(@Payload String message) {
		try {
			
			//deserializar a mensagem gravada na fila
			ClienteMessageDto dto = objectMapper.readValue(message, ClienteMessageDto.class);
			
//			enviar por email
			mailSenderComponent.sendMessage(dto.getEmailTo(), dto.getSubject(), dto.getBody());			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}	

	
	
}
