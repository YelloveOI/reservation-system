package cz.cvut.fel.meetingRoom.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract public class AbstractEntity {

    @Getter
    @Setter
    @Id
    @GeneratedValue
    Integer id;

}
