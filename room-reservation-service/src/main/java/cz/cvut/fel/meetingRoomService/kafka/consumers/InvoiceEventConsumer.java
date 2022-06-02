package cz.cvut.fel.meetingRoomService.kafka.consumers;

import cz.cvut.fel.meetingRoomService.service.interfaces.EventHandler;
import events.InvoiceEvent;
import events.ReservationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class InvoiceEventConsumer {

    private final EventHandler eventHandler;
    private final Logger logger = LoggerFactory.getLogger(InvoiceEventConsumer.class);

    public InvoiceEventConsumer(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = "${room-reservation.invoice-event-topic}", groupId = "${room-reservation.groupId}", containerFactory = "containerFactory")
    public void consume(InvoiceEvent event, Acknowledgment ack){
        logger.info("Event with id '{}' received", event.getEventId());

        try {
            eventHandler
                    .onEvent(event)
                    .thenRun(ack::acknowledge);
        } catch (Exception e) {
            logger.warn("Unable to apply event {} to the latest state of aggregate with ID {}.", event, event.getInvoiceId(), e);
            // TODO blowback
        }

    }

}
