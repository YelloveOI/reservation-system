package cz.cvut.fel.meetingRoomService.service;

import com.sun.istack.NotNull;
import cz.cvut.fel.meetingRoomService.domain.Reservation;
import cz.cvut.fel.meetingRoomService.domain.Room;
import cz.cvut.fel.meetingRoomService.enums.ReservationStatus;
import cz.cvut.fel.meetingRoomService.repository.interfaces.ReservationRepository;
import cz.cvut.fel.meetingRoomService.service.interfaces.ReservationService;
import cz.cvut.fel.meetingRoomService.service.interfaces.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository repo;

    public ReservationServiceImpl(ReservationRepository repo) {
        this.repo = repo;
    }

    @Override
    public void cancelReservation(Integer reservationId) {
        // TODO
    }

    @Override
    public void createReservation(Reservation reservation) {
        if(roomAvailable(reservation.getRoom().getId(), reservation.getDate())) {
            repo.save(reservation);
            infoLog(Reservation.class, reservation.getId(), "CREATED");
        } else {
            warnLog(Reservation.class, reservation.getId(), "ROOM " + reservation.getRoom() + " IS UNAVAILABLE");
        }
    }

    @Override
    public void updateReservationStatus(@NotNull Integer reservationId, @NotNull ReservationStatus status) {
        Optional<Reservation> reservation = repo.findById(reservationId);

        if(reservation.isEmpty()) {
            warnLog(Reservation.class, reservationId, "DOESN'T EXIST");
            return;
        }

        reservation.get().setStatus(status);
        repo.save(reservation.get());

        infoLog(Reservation.class, reservationId, "STATUS UPDATED ON" + status.toString());
    }

    @Override
    public Set<Reservation> getUserReservations(@NotNull Integer userId) {
        return repo.findAllByUserId(userId);
    }

    @Override
    public Set<Reservation> getAllRoomReservations(@NotNull Integer roomId) {
        return repo.findAllByRoomId(roomId);
    }

    @Override
    public Set<Reservation> getActiveRoomReservations(@NotNull Integer roomId) {
        Set<Reservation> result =  repo.findAllByRoomIdAndStatus(roomId, ReservationStatus.PAID);
        result.addAll(repo.findAllByRoomIdAndStatus(roomId, ReservationStatus.UNPAID));

        return result;
    }

    @Override
    public Set<Reservation> getActiveUserReservations(@NotNull Integer userId) {
        Set<Reservation> result = repo.findAllByUserIdAndStatus(userId, ReservationStatus.UNPAID);
        result.addAll(repo.findAllByUserIdAndStatus(userId, ReservationStatus.PAID));

        return result;
    }

    @Override
    public boolean roomAvailable(@NotNull Integer roomId, @NotNull Date onDate) {
        Set<Reservation> reservations = repo.findAllByRoomId(roomId);

        int totalReservationsLength = reservations.stream()
                .map(Reservation::getDurationInHours)
                .reduce(0, Integer::sum);

        return totalReservationsLength < 24;
    }

    private void infoLog(Class entityClass, Integer id, String action) {
        StringBuilder sb = new StringBuilder();

        sb
                .append("Entity ")
                .append(entityClass.toString())
                .append(" with ID ")
                .append(id)
                .append(" ")
                .append(action);

        logger.info(sb.toString());
    }

    private void warnLog(Class entityClass, Integer id, String issue) {
        StringBuilder sb = new StringBuilder();

        sb
                .append("Entity ")
                .append(entityClass.toString())
                .append(" with ID ")
                .append(id)
                .append(" ")
                .append(issue);

        logger.warn(sb.toString());
    }

}
