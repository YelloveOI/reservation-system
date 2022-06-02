package cz.cvut.fel.meetingRoomService.service;

import cz.cvut.fel.meetingRoomService.enums.ReservationStatus;
import cz.cvut.fel.meetingRoomService.service.interfaces.EventHandler;
import cz.cvut.fel.meetingRoomService.service.interfaces.ReservationService;
import events.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

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
