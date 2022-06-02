package cz.cvut.fel.meetingRoomService.repository.interfaces;

import cz.cvut.fel.meetingRoomService.domain.Reservation;
import cz.cvut.fel.meetingRoomService.domain.Room;
import cz.cvut.fel.meetingRoomService.enums.ReservationStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    Set<Reservation> findAllByUserId(Integer userId);

    Set<Reservation> findAllByUserIdAndStatus(Integer userId, ReservationStatus status);

    Set<Reservation> findAllByRoomId(Integer roomId);

    Set<Reservation> findAllByRoomIdAndStatus(Integer roomId, ReservationStatus status);

    void deleteById(Integer id);

}
