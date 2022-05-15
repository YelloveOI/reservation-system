package cz.cvut.fel.meetingRoom.repository.interfaces;

import cz.cvut.fel.meetingRoom.domain.MeetingRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRoomRepository extends CrudRepository<MeetingRoom, Integer> {
}
