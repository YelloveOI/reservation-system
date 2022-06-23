package cz.cvut.fel.roomreservationservice.service;

import cz.cvut.fel.roomreservationservice.enums.ReservationStatus;
import cz.cvut.fel.roomreservationservice.service.interfaces.EventHandler;
import cz.cvut.fel.roomreservationservice.service.interfaces.ReservationService;
import events.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Event handler for kafka events encapsulated in separate service
 */
@Service
public class EventHandlerImpl implements EventHandler {

    private final ReservationService reservationService;

    public EventHandlerImpl(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public CompletableFuture<Void> onEvent(final InvoiceEvent event) {
        return CompletableFuture.runAsync(() -> {
            if(event instanceof InvoicePayed) {
                reservationService.updateReservationStatus(
                        ((InvoicePayed) event).getReservationId(),
                        ReservationStatus.PAID
                );
            } else if(event instanceof InvoiceCreationFailed) {
                reservationService.deleteReservation(((InvoiceCreationFailed) event).getReservationId());
            }
        });
    }

}
