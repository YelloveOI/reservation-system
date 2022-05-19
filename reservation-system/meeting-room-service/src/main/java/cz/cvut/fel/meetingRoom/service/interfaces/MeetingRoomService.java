package cz.cvut.fel.meetingRoom.service.interfaces;

import cz.cvut.fel.meetingRoom.domain.Equipment;
import cz.cvut.fel.meetingRoom.domain.MeetingRoom;

import java.util.List;

public interface MeetingRoomService {

    void addEquipment(Integer meetingRoomID, Equipment equipment);

    void removeEquipment(Integer meetingRoomID, Equipment equipment);

    void blockMeetingRoom(Integer meetingRoomId);

    void unblockMeetingRoom(Integer meetingRoomId);

    void createMeetingRoom(MeetingRoom meetingRoom);

    void deleteMeetingRoom(Integer meetingRoomId);

    MeetingRoom getMeetingRoomById(Integer meetingRoomId);

    List<MeetingRoom> getMeetingRoomsByParams(/*TODO*/);

    boolean isBlocked(Integer meetingRoomId);

    void addReservation(Integer meetingRoomId, Integer reservationId);

    void removeReservation(Integer meetingRoomId, Integer reservationId);

    void updateMeetingRoom(MeetingRoom meetingRoom);


}
