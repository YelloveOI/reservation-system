package cz.cvut.fel.meetingRoomService.service.interfaces;

import cz.cvut.fel.meetingRoomService.domain.Reservation;
import cz.cvut.fel.meetingRoomService.domain.Room;
import cz.cvut.fel.meetingRoomService.enums.ReservationStatus;

import java.sql.Date;
import java.util.Set;

public interface ReservationService {

    void createReservation(Reservation reservation);

    void cancelReservation(Integer reservationId);

    void updateReservationStatus(Integer reservationId, ReservationStatus status);

    Set<Reservation> getUserReservations(Integer userId);

    Set<Reservation> getActiveUserReservations(Integer userId);

    Set<Reservation> getActiveRoomReservations(Integer roomId);

    Set<Reservation> getAllRoomReservations(Integer roomId);

    boolean roomAvailable(Integer roomId, Date onDate);

}
