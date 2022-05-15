package cz.cvut.fel.meetingRoom.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Address extends AbstractEntity {

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String street;

    @Getter
    @Setter
    private String houseNumber;

    @Getter
    @Setter
    private String postCode;

    @Getter
    @Setter
    @OneToOne
    private Building building;

}
