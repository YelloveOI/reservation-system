package events;

import lombok.Getter;
import lombok.Setter;

public class InvoiceCreationFailed extends InvoiceEvent {

    @Getter
    @Setter
    public Integer reservationId;

    public InvoiceCreationFailed() {
    }

    public InvoiceCreationFailed(Integer reservationId) {
        super(-1);
        this.reservationId = reservationId;
    }
}
