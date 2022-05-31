package cz.cvut.fel.meetingRoomService.service;

import com.sun.istack.NotNull;
import cz.cvut.fel.meetingRoomService.domain.Reservation;
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

        if(room.isEmpty()) {
            warnLog(Room.class, roomId, "DOESN'T EXIST");
            return;
        }

        room.get().setActive(false);
        repo.save(room.get());

        infoLog(Room.class, roomId, "BLOCKED");
    }

    @Override
    public void saveRoom(@NotNull Room room) {
        repo.save(room);
        infoLog(Room.class, room.getId(), "SAVED");
    }

    @Override
    public void deleteRoom(@NotNull Integer roomId) {
        Optional<Room> room = repo.findByIdAndDeletedIsFalse(roomId);

        if(room.isEmpty()) {
            warnLog(Room.class, roomId, "DOESN'T EXIST");
            return;
        }

        room.get().setDeleted(true);
        repo.save(room.get());

        infoLog(Room.class, roomId, "DELETED");
    }

    @Override
    public Set<Room> getRoomsByParams(@NotNull String city, @NotNull int capacity, @NotNull boolean isActive) {
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
