package cz.cvut.fel.invoiceservice.service.interfaces;

import events.ReservationEvent;

import java.util.concurrent.CompletableFuture;

public interface EventHandler {

    CompletableFuture<Void> onEvent(ReservationEvent event);

}
