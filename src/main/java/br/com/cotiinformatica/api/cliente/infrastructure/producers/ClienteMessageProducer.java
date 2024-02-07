package br.com.cotiinformatica.api.cliente.infrastructure.producers;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteMessageProducer {

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	Queue queue;
	/*
	 * Método para gravar uma mensagem na fila
	 */
	public void sendMessage(String message) {
		rabbitTemplate.convertAndSend(queue.getName(), message);
	}
}
