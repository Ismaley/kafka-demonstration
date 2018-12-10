package com.kafka.presentation.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.LocalTime;

@Slf4j
@SpringBootApplication
public class KafkaConsumerTestApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerTestApplication.class, args);
	}


	@KafkaListener(topics = "kafka_test_topic")
	public void consume(ConsumerRecord<?, ?> cr) {
		log.info("consumed message {} ", cr.value().toString(), LocalTime.now());
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
