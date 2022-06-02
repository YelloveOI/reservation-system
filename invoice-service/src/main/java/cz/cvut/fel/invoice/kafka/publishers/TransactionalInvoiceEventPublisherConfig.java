package cz.cvut.fel.invoice.kafka.publishers;

import cz.cvut.fel.invoice.model.Invoice;
import events.InvoiceEvent;
import events.ReservationEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TransactionalInvoiceEventPublisherConfig {

    @Bean
    public ProducerFactory<String, InvoiceEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class
        );
        configProps.put(
                ProducerConfig.TRANSACTIONAL_ID_CONFIG, "invoice-event-publisher"
        );
        configProps.put(
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,
                true
        );
        configProps.put(
                ProducerConfig.ACKS_CONFIG,
                "all"
        );
        configProps.put(
                ProducerConfig.RETRIES_CONFIG,
                3
        );
        configProps.put(
                ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,
                1
        );


        final DefaultKafkaProducerFactory<String, InvoiceEvent> factory =
                new DefaultKafkaProducerFactory<>(configProps);
        factory.setTransactionIdPrefix("invoice-event-publisher");

        return factory;
    }

    @Bean
    public KafkaTemplate<String, InvoiceEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
