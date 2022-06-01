package cz.cvut.fel.meetingRoomService.service;

import com.sun.istack.NotNull;
import cz.cvut.fel.meetingRoomService.domain.Room;
import cz.cvut.fel.meetingRoomService.repository.interfaces.RoomRepository;
import cz.cvut.fel.meetingRoomService.service.interfaces.ReservationService;
import cz.cvut.fel.meetingRoomService.service.interfaces.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repo;

    private final ReservationService reservationService;

    private final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

    public RoomServiceImpl(RoomRepository repo, ReservationService reservationService) {
        this.repo = repo;
        this.reservationService = reservationService;
    }

    @Override
    public void blockRoom(@NotNull Integer roomId) {
        Optional<Room> room = repo.findByIdAndDeletedIsFalse(roomId);

        if(room.isEmpty()) throw new IllegalArgumentException(String.format("Room with id %s not found", roomId));

        room.get().setActive(false);

        reservationService.cancelAllActiveRoomReservations(roomId);

        repo.save(room.get());

        logger.info(String.format("Room with id %s blocked"), roomId);
    }

    @Override
    public void saveRoom(@NotNull Room room) {
        repo.save(room);
    }

    @Override
    public void deleteRoom(@NotNull Integer roomId) {
        Optional<Room> room = repo.findByIdAndDeletedIsFalse(roomId);

        if(room.isEmpty()) throw new IllegalArgumentException(String.format("Room with id %s not found", roomId));

        room.get().setDeleted(true);

        reservationService.cancelAllActiveRoomReservations(roomId);

        repo.save(room.get());

        logger.info(String.format("Room with id %s deleted"), roomId);

    }

    @Override
    public Set<Room> getRoomsByParams(@NotNull String city, @NotNull int capacity, @NotNull boolean isActive) {
        if(capacity < 1) throw new IllegalArgumentException("Capacity must be higher than 0");

        return repo.findAllByCityAndCapacityAndActiveAndDeletedIsFalse(city, capacity, isActive);
    }

    @Override
    public Set<Room> getAvailableRoomsByParams(@NotNull Date onDate, @NotNull String city, @NotNull int capacity) {
        Set<Room> rooms = repo.findAllByCityAndCapacityAndActiveAndDeletedIsFalse(city, capacity, true);
        Set<Room> result = new HashSet<>();

        for(Room r : rooms) {
            if(reservationService.roomAvailable(r.getId(), onDate)) result.add(r);
        }

        return result;
    }

}
