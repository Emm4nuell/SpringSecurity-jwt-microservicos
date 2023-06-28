package br.com.processo.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.processo.dto.EmailDto;

@Component
public class EnviarEmail {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private Queue queue;
	
	public void enviarEmail(EmailDto dto) throws JsonProcessingException {
		var json = converterToJson(dto);
		System.err.println("Enviar email: " + json);
		
		// vai pegar o nome da fila e publica no brocker
		rabbitTemplate.convertAndSend(queue.getName(), json);
	}
	
	private String converterToJson(EmailDto dto) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		var json = mapper.writeValueAsString(dto);
		return json;
	}
}
