package cz.cvut.fel.meetingRoom.domain;

import cz.cvut.fel.meetingRoom.enums.EquipmentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
public class Equipment extends AbstractEntity {

    @Getter
    @Setter
    private EquipmentType equipmentType;

}
