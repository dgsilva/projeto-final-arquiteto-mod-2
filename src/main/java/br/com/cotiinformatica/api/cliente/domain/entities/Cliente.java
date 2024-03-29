package br.com.cotiinformatica.api.cliente.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID idCliente;
	private String nome;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String cpf;
	private String dataNascimento;
	@OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Endereco> enderecos = new ArrayList<>(); 
	@Column
	@JsonIgnore
	private byte[] foto;
}
