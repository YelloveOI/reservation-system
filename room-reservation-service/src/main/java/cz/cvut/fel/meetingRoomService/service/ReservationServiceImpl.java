package cz.cvut.fel.meetingRoomService.service;

import com.sun.istack.NotNull;
import cz.cvut.fel.meetingRoomService.domain.Reservation;
import cz.cvut.fel.meetingRoomService.enums.ReservationStatus;
import cz.cvut.fel.meetingRoomService.kafka.publishers.interfaces.ReservationEventPublisher;
import cz.cvut.fel.meetingRoomService.repository.interfaces.ReservationRepository;
import cz.cvut.fel.meetingRoomService.service.interfaces.ReservationService;
import events.ReservationCancelled;
import events.ReservationCreated;
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

    private final ReservationEventPublisher publisher;

    public ReservationServiceImpl(ReservationRepository repo, ReservationEventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @Override
    public void cancelReservation(Integer reservationId) {
        Optional<Reservation> reservation = repo.findById(reservationId);

        if(reservation.isEmpty()) throw new IllegalArgumentException(String.format("Reservation with id %s doesn't exist", reservationId));

        reservation.get().setStatus(ReservationStatus.CANCELLED);

        repo.save(reservation.get());
        publisher.send(new ReservationCancelled(reservationId));

        logger.info(String.format("Reservation with id %s cancelled", reservationId));
    }

    @Override
    public void cancelAllActiveRoomReservations(Integer roomId) {
        Set<Reservation> reservations = getActiveRoomReservations(roomId);

        for(Reservation r : reservations) {
            cancelReservation(r.getId());
        }

        logger.info(String.format("All reservation for room with id %s cancelled", roomId));
    }

    @Override
    public void createReservation(Reservation reservation) {
        if(
                !roomAvailable(reservation.getRoom().getId(), reservation.getDate())
        ) throw new IllegalArgumentException(String.format("Can't make reservation for room with id %s", reservation.getRoom().getId()));

        repo.save(reservation);

        publisher.send(new ReservationCreated(reservation.getId(), reservation.getUserId(), reservation.getTotalPrice()));

        logger.info(String.format("Reservation for room with id %s created", reservation.getRoom().getId()));
    }

    @Override
    public void updateReservationStatus(@NotNull Integer reservationId, @NotNull ReservationStatus status) {
        Optional<Reservation> reservation = repo.findById(reservationId);

        if(reservation.isEmpty()) throw new IllegalArgumentException(String.format("Reservation with id %s doesn't exist", reservationId));

        reservation.get().setStatus(status);
        repo.save(reservation.get());

        logger.info(String.format("Status changed for reservation with id %s on %s", reservationId, status));
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

}
