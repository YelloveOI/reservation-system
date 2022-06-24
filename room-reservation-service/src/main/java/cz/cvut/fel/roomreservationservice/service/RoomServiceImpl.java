package cz.cvut.fel.roomreservationservice.service;

import com.sun.istack.NotNull;
import cz.cvut.fel.roomreservationservice.domain.Room;
import cz.cvut.fel.roomreservationservice.repository.interfaces.RoomRepository;
import cz.cvut.fel.roomreservationservice.service.interfaces.ReservationService;
import cz.cvut.fel.roomreservationservice.service.interfaces.RoomService;
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

    /**
     * Blocks room and cancels all its reservations
     * @param roomId
     */
    @Override
    public void blockRoom(@NotNull Integer roomId) {
        Optional<Room> room = repo.findByIdAndDeletedIsFalse(roomId);

        if(room.isEmpty()) throw new IllegalArgumentException(String.format("Room with id %s not found", roomId));

        room.get().setActive(false);

        reservationService.cancelAllActiveRoomReservations(roomId);

        repo.save(room.get());

        logger.info(String.format("Room with id %s blocked", roomId));
    }

    @Override
    public void saveRoom(@NotNull Room room) {
        repo.save(room);
    }

    /**
     * Sets isDeleted flag true for room and cancels all its reservations
     * @param roomId
     */
    @Override
    public void deleteRoom(@NotNull Integer roomId) {
        Optional<Room> room = repo.findByIdAndDeletedIsFalse(roomId);

        if(room.isEmpty()) throw new IllegalArgumentException(String.format("Room with id %s not found", roomId));

        room.get().setDeleted(true);

        reservationService.cancelAllActiveRoomReservations(roomId);

        repo.save(room.get());

        logger.info(String.format("Room with id %s deleted", roomId));

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

    @Override
    public String getRoomNameById(@NotNull Integer id) {
        Optional<Room> room = repo.findById(id);

        return room.map(Room::getName).orElse(null);
    }

    @Override
    public List<Room> getDistinctCities() {
        Iterable<Room> rooms = repo.findAll();

        List<Room> distinctCities = new ArrayList<>();
        Set<String> distinctCityNames = new HashSet<>();

        for (Room room : rooms) {
            if (!distinctCityNames.contains(room.getCity())) {
                distinctCityNames.add(room.getCity());
                distinctCities.add(room);
            }
        }

        return distinctCities;
    }

    @Override
    public Set<Room> findAll() {
        Iterable<Room> rooms = repo.findAll();
        Set<Room> result = new HashSet<>();

        for (Room room : rooms) {
            result.add(room);
        }

        return result;
    }

}
