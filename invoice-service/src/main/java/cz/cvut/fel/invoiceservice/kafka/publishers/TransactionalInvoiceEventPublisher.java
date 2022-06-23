package cz.cvut.fel.invoiceservice.kafka.publishers;

import cz.cvut.fel.invoiceservice.kafka.publishers.interfaces.InvoiceEventPublisher;
import events.InvoiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class TransactionalInvoiceEventPublisher implements InvoiceEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(TransactionalInvoiceEventPublisher.class);
    private final String topicName;
    private final KafkaTemplate<String, InvoiceEvent> kafkaTemplate;

    public TransactionalInvoiceEventPublisher(
            @Value("${invoice-service.invoice-event-topic}") String topicName,
            KafkaTemplate<String, InvoiceEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Override
    public void send(InvoiceEvent event) {
        logger.info("Attempting to send {} to topic {}", event, topicName);
        String key = event.getEventId();
        kafkaTemplate.executeInTransaction(operations -> {
            operations
                    .send(topicName, String.valueOf(key), event)
                    .addCallback(this::onSuccess, this::onFailure);
            return true;
        });
    }
    private void onSuccess(SendResult<String, InvoiceEvent> result) {
        logger.info("InvoiceEvent '{}' has been written to topic-partition {}-{} with ingestion timestamp {}",
                result.getProducerRecord().key(),
                result.getProducerRecord().topic(),
                result.getProducerRecord().partition(),
                result.getProducerRecord().timestamp()
        );
    }

    private void onFailure(Throwable throwable) {
        logger.warn("Unable to write InvoiceEvent to topic {}", topicName);
    }


}
