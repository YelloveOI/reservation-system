package cz.cvut.fel.meetingRoomService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import roomreservation.events.CreateReservationEvent;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private static final String TOPIC = "users";
    private final KafkaTemplate<String, CreateReservationEvent> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, CreateReservationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(CreateReservationEvent message){
        logger.info(String.format("$$ -> Producing message --> %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }

}
