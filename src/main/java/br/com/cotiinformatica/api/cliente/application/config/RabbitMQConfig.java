package br.com.cotiinformatica.api.cliente.application.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
	
	@Value("${queue.name}")
	private String queuename;

	@Bean
	public Queue queue() {
		return new Queue(queuename);
	}
}

