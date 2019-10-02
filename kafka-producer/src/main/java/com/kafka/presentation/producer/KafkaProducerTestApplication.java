package com.kafka.presentation.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@SpringBootApplication
public class KafkaProducerTestApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerTestApplication.class, args);
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	@Autowired
	private KafkaTemplate<String, NotificationParam> template;

	@Override
	public void run(String... args) {
		for(int i = 0; i <= 10; i++) {
			NotificationParam param = buildNotificationParameter(i);
			log.info("publishing message {}", param);
			this.template.send("dynamic_topic_1", param);
			this.template.send("dynamic_topic_2", param);
		}
	}

	private NotificationParam buildNotificationParameter(int messageNumber) {
		return NotificationParam.builder()
				.userId("user" + messageNumber)
				.title("title")
				.message("message_" + messageNumber)
				.build();
	}
}
