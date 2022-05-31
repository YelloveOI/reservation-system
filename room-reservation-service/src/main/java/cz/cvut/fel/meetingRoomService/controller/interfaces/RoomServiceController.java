package cz.cvut.fel.meetingRoomService.controller.interfaces;

import cz.cvut.fel.meetingRoomService.domain.Room;
import cz.cvut.fel.meetingRoomService.domain.Reservation;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Set;

public interface RoomServiceController {

    ResponseEntity<String> entryPoint();

    ResponseEntity<Void> blockRoom(Integer roomId);

    ResponseEntity<Void> cancelReservation(Integer reservationId);

    ResponseEntity<Void> createRoom(Room room);

    ResponseEntity<Void> deleteRoom(Integer roomId);

    ResponseEntity<Set<Reservation>> getActiveRoomReservations(Integer roomId);

    ResponseEntity<Set<Reservation>> getAllRoomReservations(Integer roomId);

    ResponseEntity<Set<Room>> getAvailableRoomsByParams(Date onDate, String city, int capacity);

    ResponseEntity<Set<Reservation>> getAllUserReservations(Integer userId);

    ResponseEntity<Set<Reservation>> getActiveReservationsOfUser(Integer userId);

    ResponseEntity<Void> createReservation(Reservation reservation);

}
