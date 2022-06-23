package cz.cvut.fel.invoiceservice.kafka.publishers.interfaces;

import events.InvoiceEvent;

public interface InvoiceEventPublisher {

    void send(InvoiceEvent event);

}
