package cz.cvut.fel.invoice.model;      // ?

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

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

    /*@Getter
    @Setter
    private boolean isRemoved;*/
}
