package cz.cvut.fel.meetingRoomService.kafka;

import cz.cvut.fel.meetingRoomService.kafka.interfaces.ReservationEventPublisher;
import events.ReservationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class TransactionalReservationEventPublisher implements ReservationEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(TransactionalReservationEventPublisher.class);
    private final String topicName;
    private final KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    public TransactionalReservationEventPublisher(
            @Value("${room-reservation.topic}") String topicName,
            KafkaTemplate<String, ReservationEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Override
    public void send(ReservationEvent event) {
        logger.info("Attempting to send {} to topic {}", event, topicName);
        Integer key = event.getReservationId();
        kafkaTemplate.executeInTransaction(operations -> {
            operations
                    .send(topicName, String.valueOf(key), event)
                    .addCallback(this::onSuccess, this::onFailure);
            return true;
        });
    }
    private void onSuccess(SendResult<String, ReservationEvent> result) {
        logger.info("ReservationEvent '{}' has been written to topic-partition {}-{} with ingestion timestamp {}",
                    result.getProducerRecord().key(),
                    result.getProducerRecord().topic(),
                    result.getProducerRecord().partition(),
                    result.getProducerRecord().timestamp()
                );
    }

    private void onFailure(Throwable throwable) {
        logger.warn("Unable to write ReservationEvent to topic {}", topicName);
    }

}
