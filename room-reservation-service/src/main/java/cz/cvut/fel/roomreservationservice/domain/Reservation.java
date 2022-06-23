package cz.cvut.fel.roomreservationservice.domain;

import cz.cvut.fel.roomreservationservice.enums.ReservationStatus;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends AbstractEntity {

    private Date date;

    private int totalPrice;

    private Time timeFrom;

    private Time timeTo;

    @ManyToOne
    private Room room;

    private Integer userId;

    @Enumerated
    private ReservationStatus status;

    private int durationInHours;

}
