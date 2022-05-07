package cz.cvut.fel.NSSreservations.microservice.logic.MeetingRoom.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public enum Equipment {
    PROJECTOR,
    SMART_BOARD,
    BLACKBOARD,
    PC;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

}
