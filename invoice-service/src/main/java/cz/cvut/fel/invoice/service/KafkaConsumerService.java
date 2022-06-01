package cz.cvut.fel.invoice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import roomreservation.events.CreateReservationEvent;

@Service
public class KafkaConsumerService {

    private final InvoiceService service;
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    public KafkaConsumerService(InvoiceService service) {
        this.service = service;
    }

    @KafkaListener(topics = "users", groupId = "group_id")
    public void consume(CreateReservationEvent event){
        logger.info(String.format("$$ -> Consumed CreatedReservationEvent -> %s", event.toString()));

        service.save(event.userId, event.reservationId, event.totalPrice);

    }

}
