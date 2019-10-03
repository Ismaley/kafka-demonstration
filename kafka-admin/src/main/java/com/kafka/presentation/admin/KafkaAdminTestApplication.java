package com.kafka.presentation.admin;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class KafkaAdminTestApplication implements CommandLineRunner {

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServers;

	public static void main(String[] args) {
		SpringApplication.run(KafkaAdminTestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		final short replicationFactor = 1;
		final short partitionNumber = 32;
		final String topic1Name = "dynamic_topic_1";
		final String topic2Name = "dynamic_topic_2";

		AdminClient adminClient = KafkaAdminClient.create(buildDefaultProps());
		System.out.println("creating new kafka topics: " + Arrays.asList(topic1Name, topic2Name));
		CreateTopicsResult result = adminClient.createTopics(Arrays.asList(new NewTopic(topic1Name, partitionNumber, replicationFactor),
				new NewTopic(topic2Name, partitionNumber, replicationFactor)));


		result.values().get("dynamic_topic_1").get();
		result.values().get("dynamic_topic_2").get();

		DescribeTopicsResult describeResult = adminClient.describeTopics(Arrays.asList(topic1Name, topic2Name));
		TopicDescription topic1Description = describeResult.values().get(topic1Name).get();
		TopicDescription topic2Description = describeResult.values().get(topic2Name).get();

		System.out.println("kafka topic: " + topic1Description);
		System.out.println("kafka topic: " + topic2Description);

		Thread.sleep(5000);

		System.out.println("deleting kafka topics: " + Arrays.asList(topic1Name, topic2Name));
		DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Arrays.asList(topic1Name, topic2Name));

		System.out.println(deleteTopicsResult.values().get(topic1Name).get());
		System.out.println(deleteTopicsResult.values().get(topic2Name).get());

		DescribeTopicsResult describeResultAfterDeletion = adminClient.describeTopics(Arrays.asList(topic1Name, topic2Name));
		TopicDescription topic1DescriptionAfterDeletion = describeResultAfterDeletion.values().get(topic1Name).get();
		TopicDescription topic2DescriptionAfterDeletion = describeResultAfterDeletion.values().get(topic2Name).get();

		System.out.println("kafka topic: " + topic1DescriptionAfterDeletion);
		System.out.println("kafka topic: " + topic2DescriptionAfterDeletion);

		Thread.sleep(5000);

		adminClient.close();
	}

	private Map<String, Object> buildDefaultProps() {
		Map<String, Object> props = new HashMap<>();
		props.put("bootstrap.servers", kafkaServers);
		return props;
	}
}
