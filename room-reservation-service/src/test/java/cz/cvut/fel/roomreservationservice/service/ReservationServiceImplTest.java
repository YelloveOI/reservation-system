package cz.cvut.fel.roomreservationservice.service;

import cz.cvut.fel.roomreservationservice.domain.Reservation;
import cz.cvut.fel.roomreservationservice.domain.Room;
import cz.cvut.fel.roomreservationservice.enums.ReservationStatus;
import cz.cvut.fel.roomreservationservice.kafka.publishers.interfaces.ReservationEventPublisher;
import cz.cvut.fel.roomreservationservice.repository.interfaces.ReservationRepository;
import events.ReservationCancelled;
import events.ReservationCreated;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceImplTest {

    @Autowired
    private ReservationServiceImpl reservationService;

    @MockBean
    private ReservationRepository repository;

    @MockBean
    private ReservationEventPublisher publisher;

    @MockBean
    private Reservation reservation;

    @MockBean
    private Room room;

    @Test
    public void cancelReservation_existingReservation() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(reservation));

        reservationService.cancelReservation(1);

        Mockito.verify(reservation, Mockito.times(1)).setStatus(ReservationStatus.CANCELLED);
        Mockito.verify(repository, Mockito.times(1)).save(reservation);
        Mockito.verify(publisher, Mockito.times(1)).send(
                ArgumentMatchers.argThat(v -> v instanceof ReservationCancelled && v.getReservationId() == 1)
        );
    }

    @Test
    public void cancelReservation_nonexistentReservation() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            reservationService.cancelReservation(1);
        });
    }

    @Test
    public void roomAvailable_unavailableRoom() {
        Mockito.when(reservation.getDurationInHours()).thenReturn(24);
        Mockito.when(repository.findAllByRoomId(1)).thenReturn(Set.of(reservation));

        boolean result = reservationService.roomAvailable(1, Date.valueOf(LocalDate.now()));

        Assert.assertFalse(result);
    }

    @Test
    public void roomAvailable_availableRoom() {
        Mockito.when(reservation.getDurationInHours()).thenReturn(12);
        Mockito.when(repository.findAllByRoomId(1)).thenReturn(Set.of(reservation));

        boolean result = reservationService.roomAvailable(1, Date.valueOf(LocalDate.now()));

        Assert.assertTrue(result);
    }

    @Test
    public void createReservation_successCreation() {
        Mockito.when(room.getId()).thenReturn(1);
        Mockito.when(reservation.getRoom()).thenReturn(room);
        Mockito.when(reservation.getDate()).thenReturn(Date.valueOf(LocalDate.now()));
        Mockito.when(reservation.getUserId()).thenReturn(1);
        Mockito.when(reservation.getTotalPrice()).thenReturn(1);
        Mockito.when(reservation.getId()).thenReturn(1);
        Mockito.when(reservation.getDurationInHours()).thenReturn(12);
        Mockito.when(repository.findAllByRoomId(1)).thenReturn(Set.of(reservation));

        reservationService.createReservation(reservation);

        Mockito.verify(repository, Mockito.times(1)).save(reservation);
        Mockito.verify(publisher, Mockito.times(1)).send(
                ArgumentMatchers.argThat(v -> v instanceof ReservationCreated && v.getReservationId() == 1)
        );
    }

    @Test
    public void createReservation_failedCreation() {
        Mockito.when(reservation.getDurationInHours()).thenReturn(24);
        Mockito.when(repository.findAllByRoomId(1)).thenReturn(Set.of(reservation));
        Mockito.when(reservation.getRoom()).thenReturn(room);
        Mockito.when(room.getId()).thenReturn(1);

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            reservationService.createReservation(reservation);
        });
    }


    @Test
    public void updateReservationStatus_paidStatus() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(reservation));

        reservationService.updateReservationStatus(1, ReservationStatus.PAID);

        Mockito.verify(repository, Mockito.times(1)).save(reservation);
        Mockito.verify(reservation, Mockito.times(1)).setStatus(ReservationStatus.PAID);
    }

    @Test
    public void updateReservationStatus_failedUpdate() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            reservationService.updateReservationStatus(1, ReservationStatus.PAID);
        });
    }
}
