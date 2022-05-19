package cz.cvut.fel.meetingRoom.domain;

import cz.cvut.fel.meetingRoom.enums.EquipmentType;
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
    private List<Integer> reservations;

    @Getter
    @Setter
    @OneToMany
    private List<Equipment> equipmentTypes;

    public void addReservationId(Integer reservationId) {
        reservations.add(reservationId);
    }

    public void removeReservationId(Integer reservationId) {
        reservations.remove(reservationId);
    }

    public void addEquipment(Equipment equipment) {
        equipmentTypes.add(equipment);
    }

    public void removeEquipment(Equipment equipment) {
        equipmentTypes.remove(equipment);
    }

    public boolean hasEquipment(Equipment equipment) {
        return equipmentTypes.contains(equipment);
    }

}
