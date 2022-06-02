package cz.cvut.fel.invoice.service;

import cz.cvut.fel.invoice.kafka.publishers.interfaces.InvoiceEventPublisher;
import cz.cvut.fel.invoice.service.interfaces.EventHandler;
import cz.cvut.fel.invoice.service.interfaces.InvoiceService;
import events.InvoiceCreationFailed;
import events.ReservationCancelled;
import events.ReservationCreated;
import events.ReservationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EventHandlerImpl implements EventHandler {

    private final InvoiceService service;

    private final Logger logger = LoggerFactory.getLogger(EventHandlerImpl.class);

    private final InvoiceEventPublisher publisher;

    public EventHandlerImpl(InvoiceService service, InvoiceEventPublisher publisher) {
        this.service = service;
        this.publisher = publisher;
    }

    @Override
    public CompletableFuture<Void> onEvent(final ReservationEvent event) {
        return CompletableFuture.runAsync(() -> {
            if(event instanceof ReservationCancelled) {
                service.deleteAllByReservationId(event.getReservationId());
            } else if (event instanceof ReservationCreated) {
                try {
                    service.save(((ReservationCreated) event).getUserId(), event.getReservationId(), ((ReservationCreated) event).getTotalPrice());
                } catch (IllegalArgumentException e) {
                    logger.warn("Illegal argument exception during processing event '{}'", event);
                    publisher.send(new InvoiceCreationFailed(event.getReservationId()));
                }
            }
        });
    }

}
