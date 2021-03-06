package cz.cvut.fel.roomreservationservice.controller;

import cz.cvut.fel.roomreservationservice.controller.interfaces.RoomServiceController;
import cz.cvut.fel.roomreservationservice.domain.Room;
import cz.cvut.fel.roomreservationservice.domain.Reservation;
import cz.cvut.fel.roomreservationservice.dto.FindRoomDto;
import cz.cvut.fel.roomreservationservice.service.interfaces.ReservationService;
import cz.cvut.fel.roomreservationservice.service.interfaces.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import events.ReservationCreated;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/room-reservation-service")
public class RoomServiceControllerImpl implements RoomServiceController {

    private final ReservationService reservationService;
    private final RoomService roomService;
    private final Logger logger = LoggerFactory.getLogger(RoomServiceControllerImpl.class);

    public RoomServiceControllerImpl(ReservationService reservationService, RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    @Override
    @GetMapping(value = "")
    public ResponseEntity<String> entryPoint() {
        logger.info("GET entryPoint/ request");
        return new ResponseEntity<>("RoomReservationService REST v1", HttpStatus.valueOf(200));
    }

    @Override
    @GetMapping(value = "/cities")
    public ResponseEntity<List<Room>> getCities() {
        logger.info("GET cities/ request");
        return ResponseEntity.ok(roomService.getDistinctCities());
    }

    @Override
    @PatchMapping(value = "/room/{id}")
    public ResponseEntity<Void> blockRoom(@PathVariable("id") Integer roomId) {
        logger.info("PATCH room/{id} request");
        roomService.blockRoom(roomId);
        return ResponseEntity.ok(null);
    }

    @Override
    @PatchMapping(value = "/room-name/{id}")
    public ResponseEntity<String> getRoomNameById(@PathVariable("id") Integer roomId) {
        logger.info("PATCH room-name/{id} request");
        return ResponseEntity.ok(roomService.getRoomNameById(roomId));
    }

    @Override
    @GetMapping(value = "/rooms")
    public ResponseEntity<Set<Room>> getRooms() {
        logger.info("GET rooms/ request");
        return ResponseEntity.ok(roomService.findAll());
    }

    @Override
    @PatchMapping(value = "/reservation/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("id") Integer reservationId) {
        logger.info("PATCH reservation/{id} request");
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok(null);
    }

    @Override
    @PostMapping(value = "/rooms")
    public ResponseEntity<Void> createRoom(@RequestBody Room room) {
        logger.info("POST rooms/ request with body '{}'", room.toString());
        roomService.saveRoom(room);
        return ResponseEntity.ok(null);
    }

    @Override
    @DeleteMapping(value = "/room/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") Integer roomId) {
        logger.info("DELETE room/{id} request");
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok(null);
    }

    @Override
    @GetMapping(value = "/reservations/activeByRoomId/{id}")
    public ResponseEntity<Set<Reservation>> getActiveRoomReservations(@PathVariable("id") Integer roomId) {
        logger.info("GET reservations/activeByRoomId/{id} request");
        return ResponseEntity.ok(reservationService.getActiveRoomReservations(roomId));
    }

    @Override
    @GetMapping(value = "/reservations/allByRoomId/{id}")
    public ResponseEntity<Set<Reservation>> getAllRoomReservations(@PathVariable("id") Integer roomId) {
        logger.info("GET reservations/allByRoomId/{id} request");
        return ResponseEntity.ok(reservationService.getAllRoomReservations(roomId));
    }

    @Override
    @GetMapping(value = "/rooms/byParams")
    public ResponseEntity<Set<Room>> getAvailableRoomsByParams(@RequestBody FindRoomDto findRoomDto) {
        logger.info("GET rooms/byParams/ request");
        return ResponseEntity.ok(roomService.getAvailableRoomsByParams(findRoomDto.getOnDate(), findRoomDto.getCity(), findRoomDto.getCapacity()));
    }

    @Override
    @GetMapping(value = "/reservations/allByUserId/{id}")
    public ResponseEntity<Set<Reservation>> getAllUserReservations(@PathVariable("id") Integer userId) {
        logger.info("GET reservations/allByUserId/{id} request");
        return ResponseEntity.ok(reservationService.getUserReservations(userId));
    }

    @Override
    @GetMapping(value = "/reservations/activeByUserId/{id}")
    public ResponseEntity<Set<Reservation>> getActiveReservationsOfUser(@PathVariable("id") Integer userId) {
        logger.info("GET reservations/activeByUserId/{id} request");
        return ResponseEntity.ok(reservationService.getActiveUserReservations(userId));
    }

    @Override
    @PostMapping(value = "/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody Reservation reservation) {
        logger.info("POST reservations/ request");

        reservationService.createReservation(reservation);

        ReservationCreated event = new ReservationCreated(
                reservation.getUserId(),
                reservation.getId(),
                reservation.getTotalPrice()
        );

        return ResponseEntity.ok(null);
    }
}
