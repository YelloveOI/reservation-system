package events;

import lombok.Getter;
import lombok.Setter;

public class InvoicePayed extends InvoiceEvent {

    @Getter
    @Setter
    public Integer reservationId;

    public InvoicePayed() {
    }

    public InvoicePayed(Integer invoiceId, Integer reservationId) {
        super(invoiceId);
        this.reservationId = reservationId;
    }

}
