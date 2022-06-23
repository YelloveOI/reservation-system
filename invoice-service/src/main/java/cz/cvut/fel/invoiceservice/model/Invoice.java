package cz.cvut.fel.invoiceservice.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

@Entity
public class Invoice extends AbstractEntity {

    @Getter
    @Setter
    private Integer amount;

    @Getter
    @Setter
    private LocalDate creationDate;

    @Getter
    @Setter
    private LocalTime creationTime;

    @Getter
    @Setter
    private LocalDate PaymentDeadline;

    @Getter
    @Setter
    private boolean paid;

    @Getter
    @Setter
    private boolean isRemoved;


    @Getter
    @Setter
    private Integer ownerId;

    @Getter
    @Setter
    private Integer reservationId;

    public Invoice(Integer ownerId, Integer reservationId, Integer amount) {
        this.ownerId = ownerId;
        this.reservationId = reservationId;
        this.amount = amount;

        creationDate = LocalDate.now();
        creationTime = LocalTime.now();
        PaymentDeadline = LocalDate.now().plus(Period.ofDays(15));
        paid = false;
        isRemoved = false;
    }

    public Invoice() {}

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        Invoice c = (Invoice) o;
        return getId().equals(c.getId());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                    .append(getId())
                    .toHashCode();
    }
}
