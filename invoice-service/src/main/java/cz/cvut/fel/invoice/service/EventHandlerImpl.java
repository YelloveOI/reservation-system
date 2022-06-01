package cz.cvut.fel.invoice.service;

import cz.cvut.fel.invoice.service.interfaces.EventHandler;
import cz.cvut.fel.invoice.service.interfaces.InvoiceService;
import events.ReservationCancelled;
import events.ReservationCreated;
import events.ReservationEvent;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EventHandlerImpl implements EventHandler {

    private final InvoiceService service;

    public EventHandlerImpl(InvoiceService service) {
        this.service = service;
    }

    @Override
    public CompletableFuture<Void> onEvent(final ReservationEvent event) {
        return CompletableFuture.runAsync(() -> {
            if(event instanceof ReservationCancelled) {
                service.cancelInvoiceToReservation(event.getReservationId());
            } else if (event instanceof ReservationCreated) {
                service.save(((ReservationCreated) event).getUserId(), event.getReservationId(), ((ReservationCreated) event).getTotalPrice());
            }
        });
    }
}
