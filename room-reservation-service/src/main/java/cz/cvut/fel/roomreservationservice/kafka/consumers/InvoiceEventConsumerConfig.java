package cz.cvut.fel.roomreservationservice.kafka.consumers;

import events.InvoiceEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka consumer configuration, notice AUTO_COMMIT is disabled,
 * manual commit confirmation used
 */
@Configuration
public class InvoiceEventConsumerConfig {

    @Bean
    public ConsumerFactory<String, InvoiceEvent> consumerFactory() {
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

        DefaultKafkaConsumerFactory<String, InvoiceEvent> factory = new DefaultKafkaConsumerFactory<>(props);

        return factory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, InvoiceEvent> containerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InvoiceEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(
                ContainerProperties.AckMode.MANUAL
        );

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InvoiceEvent>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InvoiceEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
