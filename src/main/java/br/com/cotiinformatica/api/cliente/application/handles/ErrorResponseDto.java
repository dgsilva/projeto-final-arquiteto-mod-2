package br.com.cotiinformatica.api.cliente.application.handles;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorResponseDto {
	private HttpStatus status;
	private List<String> errors;
}
