package br.com.processo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
	
	@Value("${mq.queues.chat-mensagem}")
	private String queue;
	
	@Bean
	public Queue queue() {
		return new Queue(queue, true);
	}
}
