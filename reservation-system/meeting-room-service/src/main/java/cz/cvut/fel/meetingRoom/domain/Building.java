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
    private String number;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String postCode;

    @Getter
    @Setter
    private String street;

    @Getter
    @Setter
    @OneToMany
    private List<MeetingRoom> roomList;

    public void addMeetingRoom(MeetingRoom meetingRoom) {
        roomList.add(meetingRoom);
    }

    public void removeMeetingRoom(MeetingRoom meetingRoom) {
        roomList.remove(meetingRoom);
    }

}
