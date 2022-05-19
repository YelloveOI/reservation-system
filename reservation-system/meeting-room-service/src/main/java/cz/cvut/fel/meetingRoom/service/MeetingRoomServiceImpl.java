package cz.cvut.fel.meetingRoom.service;

import cz.cvut.fel.meetingRoom.domain.Equipment;
import cz.cvut.fel.meetingRoom.domain.MeetingRoom;
import cz.cvut.fel.meetingRoom.enums.EquipmentType;
import cz.cvut.fel.meetingRoom.repository.interfaces.MeetingRoomRepository;
import cz.cvut.fel.meetingRoom.service.interfaces.MeetingRoomService;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final MeetingRoomRepository repo;

    public MeetingRoomServiceImpl(MeetingRoomRepository repo) {
        this.repo = repo;
    }

    @Override
    public void addEquipment(Integer meetingRoomId, Equipment equipment) {
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);

        meetingRoom.addEquipment(equipment);

        repo.save(meetingRoom);
    }

    @Override
    public void removeEquipment(Integer meetingRoomId, Equipment equipment) {
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);

        meetingRoom.removeEquipment(equipment);

        repo.save(meetingRoom);
    }

    @Override
    public void blockMeetingRoom(Integer meetingRoomId) {
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);

        meetingRoom.setActive(false);

        repo.save(meetingRoom);
    }

    @Override
    public void unblockMeetingRoom(Integer meetingRoomId) {
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);

        meetingRoom.setActive(true);

        repo.save(meetingRoom);
    }

    @Override
    public void createMeetingRoom(MeetingRoom meetingRoom) {
        repo.save(meetingRoom);
    }

    @Override
    public void deleteMeetingRoom(Integer meetingRoomId) {
        repo.deleteById(meetingRoomId);
    }

    @Override
    public MeetingRoom getMeetingRoomById(Integer meetingRoomId) {
        Optional<MeetingRoom> meetingRoom = repo.findById(meetingRoomId);

        if(meetingRoom.isPresent()) {
            return meetingRoom.get();
        } else {
            throw new PersistenceException("MeetingRoom with ID: " + meetingRoomId + "not found");
        }
    }

    @Override
    public List<MeetingRoom> getMeetingRoomsByParams() {
        //TODO
        return null;
    }

    @Override
    public boolean isBlocked(Integer meetingRoomId) {
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);

        return !meetingRoom.isActive();
    }

    @Override
    public void addReservation(Integer meetingRoomId, Integer reservationId) {
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);

        meetingRoom.addReservationId(reservationId);
    }

    @Override
    public void removeReservation(Integer meetingRoomId, Integer reservationId) {
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);

        meetingRoom.removeReservationId(reservationId);
    }

    @Override
    public void updateMeetingRoom(MeetingRoom meetingRoom) {
        repo.save(meetingRoom);
    }

}
