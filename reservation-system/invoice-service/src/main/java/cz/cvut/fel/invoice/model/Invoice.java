package cz.cvut.fel.invoice.model;      // ?

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
    @ManyToOne
    private User owner;

    @Getter
    @Setter
    private boolean paid;

    @Getter
    @Setter
    private boolean isRemoved;

    public Invoice(Integer amount) {
        this.amount = amount;
        creationDate = LocalDate.now();
        creationTime = LocalTime.now();
        PaymentDeadline = LocalDate.now().plus(Period.ofDays(15));
        owner = SecurityUtils.getCurrentUser();
        paid = false;
        isRemoved = false;
    }

    public Invoice() {}
}
