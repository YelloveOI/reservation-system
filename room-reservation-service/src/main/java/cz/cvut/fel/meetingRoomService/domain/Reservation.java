package cz.cvut.fel.meetingRoomService.domain;

import cz.cvut.fel.meetingRoomService.enums.ReservationStatus;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Reservation extends AbstractEntity {

    @Getter
    @Setter
    private Date date;

    @Getter
    @Setter
    private int totalPrice;

    @Getter
    @Setter
    private Time timeFrom;

    @Getter
    @Setter
    private Time timeTo;

    @Getter
    @Setter
    @ManyToOne
    private Room room;

    @Getter
    @Setter
    private Integer userId;

    @Getter
    @Setter
    @Enumerated
    private ReservationStatus status;

    @Getter
    @Setter
    private int durationInHours;

}
