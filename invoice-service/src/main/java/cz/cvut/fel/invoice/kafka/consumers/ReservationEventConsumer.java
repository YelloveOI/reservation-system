package cz.cvut.fel.invoice.kafka.consumers;

import cz.cvut.fel.invoice.kafka.publishers.TransactionalInvoiceEventPublisher;
import cz.cvut.fel.invoice.service.interfaces.EventHandler;
import events.InvoiceCreationFailed;
import events.ReservationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class ReservationEventConsumer {

    private final EventHandler eventHandler;
    private final Logger logger = LoggerFactory.getLogger(ReservationEventConsumer.class);

    public ReservationEventConsumer(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = "${invoice-service.reservation-event-topic}", groupId = "${invoice-service.groupId}", containerFactory = "containerFactory")
    public void consume(ReservationEvent event, Acknowledgment ack){
        logger.info("ReservationEvent with id '{}' received", event.getEventId());

        try {
            eventHandler
                    .onEvent(event)
                    .thenRun(ack::acknowledge);
        }
        catch (Exception e) {
            logger.warn("Unable to apply event {} to the latest state of aggregate with ID {}.", event, event.getReservationId(), e);
        }

    }

}
