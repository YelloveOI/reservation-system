package cz.cvut.fel.meetingRoomService.controller;

import cz.cvut.fel.meetingRoomService.controller.interfaces.RoomServiceController;
import cz.cvut.fel.meetingRoomService.domain.Room;
import cz.cvut.fel.meetingRoomService.domain.Reservation;
import cz.cvut.fel.meetingRoomService.service.Producer;
import cz.cvut.fel.meetingRoomService.service.interfaces.ReservationService;
import cz.cvut.fel.meetingRoomService.service.interfaces.RoomService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Set;

@RestController
@RequestMapping(value = "/rest/v1")
public class RoomServiceControllerImpl implements RoomServiceController {

    private final ReservationService reservationService;

    private final RoomService roomService;

    private final Producer producer;

    public RoomServiceControllerImpl(ReservationService reservationService, RoomService roomService, Producer producer) {
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.producer = producer;
    }

    @Override
    @GetMapping(value = "")
    public ResponseEntity<String> entryPoint() {
//        producer.sendMessage();
        return new ResponseEntity<>("RoomReservationService REST v1", HttpStatusCode.valueOf(200));
    }

    @Override
    @PatchMapping(value = "room/{id}")
    public ResponseEntity<Void> blockRoom(@PathVariable("id") Integer roomId) {
        roomService.blockRoom(roomId);

        return ResponseEntity.ok(null);
    }

    @Override
    @PatchMapping(value = "reservation/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("id") Integer reservationId) {
        reservationService.cancelReservation(reservationId);

        return ResponseEntity.ok(null);
    }

    @Override
    @PostMapping(value = "rooms/")
    public ResponseEntity<Void> createRoom(@RequestBody Room room) {
        roomService.saveRoom(room);

        return ResponseEntity.ok(null);
    }

    @Override
    @DeleteMapping(value = "room/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") Integer roomId) {
        roomService.deleteRoom(roomId);

        return ResponseEntity.ok(null);
    }

    @Override
    @GetMapping(value = "reservations/activeByRoomId/{id}")
    public ResponseEntity<Set<Reservation>> getActiveRoomReservations(@PathVariable("id") Integer roomId) {
        return ResponseEntity.ok(reservationService.getActiveRoomReservations(roomId));
    }

    @Override
    @GetMapping(value = "reservations/allByRoomId/{id}")
    public ResponseEntity<Set<Reservation>> getAllRoomReservations(@PathVariable("id") Integer roomId) {
        return ResponseEntity.ok(reservationService.getAllRoomReservations(roomId));
    }

    @Override
    @GetMapping(value = "rooms/byParams/")
    public ResponseEntity<Set<Room>> getAvailableRoomsByParams(@RequestBody Date onDate, @RequestBody String city, @RequestBody int capacity) {
        return ResponseEntity.ok(roomService.getAvailableRoomsByParams(onDate, city, capacity));
    }

    @Override
    @GetMapping(value = "/reservations/allByUserId/{id}")
    public ResponseEntity<Set<Reservation>> getAllUserReservations(@PathVariable("id") Integer userId) {
        return ResponseEntity.ok(reservationService.getUserReservations(userId));
    }

    @Override
    @GetMapping(value = "reservations/activeByUserId/{id}")
    public ResponseEntity<Set<Reservation>> getActiveReservationsOfUser(@PathVariable("id") Integer userId) {
        return ResponseEntity.ok(reservationService.getActiveUserReservations(userId));
    }

    @Override
    @PostMapping(value = "reservations/")
    public ResponseEntity<Void> createReservation(@RequestBody Reservation reservation) {
        reservationService.createReservation(reservation);

        return ResponseEntity.ok(null);
    }
}
