package cz.cvut.fel.invoice.kafka.publishers.interfaces;

import events.InvoiceEvent;

public interface InvoiceEventPublisher {

    void send(InvoiceEvent event);

}
