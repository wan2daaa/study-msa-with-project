package me.wane.common;

import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoggingProducer {

  private final KafkaProducer<String, String> producer;

  private final String topic;

  public LoggingProducer(
      @Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
      @Value("${logging.topic}") String topic
      ) {

    //Producer Initialization
    Properties props = new Properties();

    // kafka:29092
    props.put("bootstrap.servers", bootstrapServers);

    //kafka Cluster 에 producer 할때, key value쌍으로 보낼 텐데, key, value를 어느 데이터로써 판별하고 serialize 할 것인지
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    this.producer = new KafkaProducer<>(props);
    this.topic = topic;
  }

  // Kafka Cluseter [key, value] produce 하는 과정
  public void sendMessage(String key, String value) {
    ProducerRecord<String, String> record = new ProducerRecord<>(topic, key,
        value);

    producer.send(record, (metadata, exception) -> {
      if (exception == null) {
        System.out.println("Message sent successfully. Offset: "+ metadata.offset());
      } else {
        System.out.println("Failed to send message : "+ exception.getMessage());
      }
    });
  }


}
