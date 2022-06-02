package cz.cvut.fel.invoice.kafka.consumers;

import events.ReservationEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ReservationEventConsumerConfig {

    @Bean
    public ConsumerFactory<String, ReservationEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "${invoice-service.groupId}");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        props.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "latest"
        );
        props.put(
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                false
        );
        props.put(
                ConsumerConfig.ISOLATION_LEVEL_CONFIG,
                "read_committed"
        );
        props.put(
                JsonDeserializer.TRUSTED_PACKAGES,
                "*"
        );

        DefaultKafkaConsumerFactory<String, ReservationEvent> factory = new DefaultKafkaConsumerFactory<>(props);

        return factory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, ReservationEvent> containerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReservationEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(
                ContainerProperties.AckMode.MANUAL
        );

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReservationEvent>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReservationEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}