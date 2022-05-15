package cz.cvut.fel.meetingRoom.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Building extends AbstractEntity {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @OneToMany
    private List<MeetingRoom> roomList;

}
