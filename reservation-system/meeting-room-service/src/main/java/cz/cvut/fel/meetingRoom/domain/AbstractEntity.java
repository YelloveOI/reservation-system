package cz.cvut.fel.NSSreservations.microservice.logic.MeetingRoom.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

}
