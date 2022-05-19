package cz.cvut.fel.invoice.model;

import cz.cvut.fel.invoice.security.SecurityUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    private Integer ownerId;

    @Getter
    @Setter
    @OneToMany
    private Integer reservationId;

    @Getter
    @Setter
    private boolean paid;

    @Getter
    @Setter
    private boolean isRemoved;

    public Invoice(Integer reservationId, Integer amount) {
        this.reservationId = reservationId;
        this.amount = amount;

        creationDate = LocalDate.now();
        creationTime = LocalTime.now();
        PaymentDeadline = LocalDate.now().plus(Period.ofDays(15));
        ownerId = SecurityUtils.getCurrentUser().getId();
        paid = false;
        isRemoved = false;
    }

    public Invoice() {}
}
