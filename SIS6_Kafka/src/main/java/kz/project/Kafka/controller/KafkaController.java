package kz.project.Kafka.controller;

import kz.project.Kafka.model.Farewell;
import kz.project.Kafka.model.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class KafkaController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Object> multiTypeKafkaTemplate;

    private final String message = "hello world";

    @GetMapping
    public void sendMessage() {
        String topicName = "topic";
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });    }

    @GetMapping("multi-type")
    public void multiTypeKafkaTemplate() {
        multiTypeKafkaTemplate.send("topic", new Greeting("Greetings", "World!"));
        multiTypeKafkaTemplate.send("topic", new Farewell("Farewell", 25));
        multiTypeKafkaTemplate.send("topic", "Simple string message");
    }

}
