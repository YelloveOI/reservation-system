package cz.cvut.fel.meetingRoom.domain;

import cz.cvut.fel.meetingRoom.enums.Equipment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class MeetingRoom extends AbstractEntity {

    @Getter
    @Setter
    private int capacity;

    @Getter
    @Setter
    private int floorNumber;

    @Getter
    @Setter
    private boolean isActive;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int pricePerHour;

    @Getter
    @Setter
    private boolean prioritized;

    @Getter
    @Setter
    @OneToMany
    private List<String> equipments;

    public void addEquipment(Equipment equipment) {
        equipments.add(equipment.name());
    }

    public void removeEquipment(Equipment equipment) {
        equipments.remove(equipment.name());
    }

    public boolean hasEquipment(Equipment equipment) {
        return equipments.contains(equipment.name());
    }

}
