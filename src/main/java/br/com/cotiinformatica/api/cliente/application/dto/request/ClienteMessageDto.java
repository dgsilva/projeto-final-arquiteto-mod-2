package br.com.cotiinformatica.api.cliente.application.dto.request;

import lombok.Data;

@Data
public class ClienteMessageDto {

	private String emailTo;
	private String subject;
	private String body;
}
