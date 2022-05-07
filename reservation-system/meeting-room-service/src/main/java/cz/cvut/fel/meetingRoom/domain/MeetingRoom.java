package cz.cvut.fel.NSSreservations.microservice.logic.MeetingRoom.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class MeetingRoom extends AbstractEntity {

    @Getter
    @Setter
    private Integer capacity;

    @Getter
    @Setter
    private Integer floorNumber;

    @Getter
    @Setter
    private Boolean isActive;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer pricePerHour;

    @Getter
    @Setter
    private Boolean isPrior;

    @Getter
    @Setter
    @ManyToOne
    private Building building;

    @Getter
    @Setter
    @ManyToMany
    private List<Equipment> equipmentList;


}
