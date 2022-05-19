package cz.cvut.fel.meetingRoom.service.interfaces;

import cz.cvut.fel.meetingRoom.domain.Building;
import cz.cvut.fel.meetingRoom.domain.MeetingRoom;

public interface BuildingService {

    void addBuilding(Building building);

    void deleteBuilding(Integer buildingId);

    void addMeetingRoom(Integer buildingId, MeetingRoom meetingRoom);

    void removeMeetingRoom(Integer buildingId, MeetingRoom meetingRoom);

}
