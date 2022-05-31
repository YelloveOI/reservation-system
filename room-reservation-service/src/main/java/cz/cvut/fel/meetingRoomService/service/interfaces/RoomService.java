package cz.cvut.fel.meetingRoomService.service.interfaces;

import cz.cvut.fel.meetingRoomService.domain.Reservation;
import cz.cvut.fel.meetingRoomService.domain.Room;

import java.sql.Date;
import java.util.Set;

public interface RoomService {

    void blockRoom(Integer roomId);

    void saveRoom(Room room);

    void deleteRoom(Integer roomId);

    Set<Room> getRoomsByParams(String city, int capacity, boolean isActive);

    Set<Room> getAvailableRoomsByParams(Date onDate, String city, int capacity);


}
