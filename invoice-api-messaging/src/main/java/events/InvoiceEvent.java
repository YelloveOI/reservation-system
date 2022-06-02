package events;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public abstract class InvoiceEvent {

    @Getter
    @Setter
    public String eventId;
    @Getter
    @Setter
    public Long timestamp;
    @Getter
    @Setter
    public Integer invoiceId;

    public InvoiceEvent() {
    }

    public InvoiceEvent(Integer invoiceId) {
        this(UUID.randomUUID().toString().substring(0, 7), System.currentTimeMillis(), invoiceId);
    }

    public InvoiceEvent(String eventId, Long timestamp, Integer invoiceId) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.invoiceId = invoiceId;
    }

}
