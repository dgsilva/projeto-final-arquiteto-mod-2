package br.com.cotiinformatica.api.cliente.application.handles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cotiinformatica.api.cliente.application.dto.request.ErrorResponseDto;
import br.com.cotiinformatica.api.cliente.domain.exception.ClienteException;

@ControllerAdvice
public class ClienteExceptionHandler {

	@ExceptionHandler(ClienteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponseDto handleUserException(ClienteException e) {
		
		List<String> errors = new ArrayList<String>();
		errors.add(e.getMessage());
		
		ErrorResponseDto response = new ErrorResponseDto();
		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setErrors(errors);
		
		return response;
	}
}
