package events;

import lombok.Getter;
import lombok.Setter;

public class InvoicePaid extends InvoiceEvent {

    @Getter
    @Setter
    public Integer reservationId;

    public InvoicePaid() {
    }

    public InvoicePaid(Integer invoiceId, Integer reservationId) {
        super(invoiceId);
        this.reservationId = reservationId;
    }

}
