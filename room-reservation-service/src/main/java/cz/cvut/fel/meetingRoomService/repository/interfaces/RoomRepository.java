package cz.cvut.fel.meetingRoomService.repository.interfaces;

import cz.cvut.fel.meetingRoomService.domain.Room;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

    Optional<Room> findByIdAndDeletedIsFalse(Integer integer);

    @Query("select r from Room r where r.city = ?1 and r.capacity >= ?2 and r.active = ?3 and r.deleted = false")
    Set<Room> findAllByCityAndCapacityAndActiveAndDeletedIsFalse(String city, int capacity, boolean isActive);
}
