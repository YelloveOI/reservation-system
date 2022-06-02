package cz.cvut.fel.meetingRoomService.service.interfaces;

import events.InvoiceEvent;
import events.ReservationEvent;

import java.util.concurrent.CompletableFuture;

public interface EventHandler {

    CompletableFuture<Void> onEvent(InvoiceEvent event);

}
