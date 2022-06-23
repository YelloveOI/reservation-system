package cz.cvut.fel.roomreservationservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
public class Room extends AbstractEntity {

    @Getter
    @Setter
    private int capacity;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private int floorNumber;

    @Getter
    @Setter
    private boolean active;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int pricePerHour;

    @Getter
    @Setter
    private boolean deleted;

    @Override
    public String toString() {
        return "Room{" +
                "capacity=" + capacity +
                ", city='" + city + '\'' +
                ", floorNumber=" + floorNumber +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", deleted=" + deleted +
                '}';
    }
}
