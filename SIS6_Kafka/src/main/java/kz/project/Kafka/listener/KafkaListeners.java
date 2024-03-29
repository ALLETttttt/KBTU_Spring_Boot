package kz.project.Kafka.listener;

import kz.project.Kafka.model.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
public class KafkaListeners {
    @KafkaListener(topics = "topic", groupId = "foo")
    public void listenGroupFoo(String message) {
        log.info("\"Received Message in group foo: \" + message");
        System.out.println("Received Message in group foo: " + message);
    }


    @KafkaListener(topics = "topic")
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        System.out.println(
                "Received Message: " + message
                        + "from partition: " + partition);
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = "topic",
                    partitionOffsets = {
                            @PartitionOffset(partition = "0", initialOffset = "0"),
                            @PartitionOffset(partition = "3", initialOffset = "0")}),
            containerFactory = "partitionsKafkaListenerContainerFactory")
    public void listenToPartition(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        System.out.println(
                "Received Message: " + message
                        + "from partition: " + partition);
    }

    @KafkaListener(
            topics = "topic",
            containerFactory = "filterKafkaListenerContainerFactory")
    public void listenWithFilter(String message) {
        System.out.println("Received Message in filtered listener: " + message);
    }

    @KafkaListener(
            topics = "topic",
            containerFactory = "greetingKafkaListenerContainerFactory")
    public void greetingListener(Greeting greeting) {
        log.info("Received from listener: " + greeting.toString());
    }
}
