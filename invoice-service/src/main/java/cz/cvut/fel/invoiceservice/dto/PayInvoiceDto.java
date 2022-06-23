package cz.cvut.fel.invoiceservice.dto;

import lombok.Getter;
import lombok.Setter;

public class PayInvoiceDto {

    @Getter
    @Setter
    private Integer ownerId;

    @Getter
    @Setter
    private Integer invoiceId;

    public PayInvoiceDto(Integer ownerId, Integer invoiceId) {
        this.ownerId = ownerId;
        this.invoiceId = invoiceId;
    }

    public PayInvoiceDto() {
    }
}
