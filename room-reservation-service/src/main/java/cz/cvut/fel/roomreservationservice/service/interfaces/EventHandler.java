package cz.cvut.fel.roomreservationservice.service.interfaces;

import events.InvoiceEvent;

import java.util.concurrent.CompletableFuture;

public interface EventHandler {

    CompletableFuture<Void> onEvent(InvoiceEvent event);

}
