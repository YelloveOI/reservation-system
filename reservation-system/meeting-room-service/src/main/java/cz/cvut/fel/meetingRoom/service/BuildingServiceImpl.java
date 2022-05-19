package cz.cvut.fel.meetingRoom.service;

import cz.cvut.fel.meetingRoom.domain.Building;
import cz.cvut.fel.meetingRoom.domain.MeetingRoom;
import cz.cvut.fel.meetingRoom.repository.interfaces.BuildingRepository;
import cz.cvut.fel.meetingRoom.service.interfaces.BuildingService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository repo;

    public BuildingServiceImpl(BuildingRepository repo) {
        this.repo = repo;
    }

    @Override
    public void addBuilding(Building building) {
        repo.save(building);
    }

    @Override
    public void deleteBuilding(Integer buildingId) {
        repo.deleteById(buildingId);
    }

    @Override
    public void addMeetingRoom(Integer buildingId, MeetingRoom meetingRoom) {
        Optional<Building> building = repo.findById(buildingId);

        building.ifPresent(value -> value.addMeetingRoom(meetingRoom));
    }

    @Override
    public void removeMeetingRoom(Integer buildingId, MeetingRoom meetingRoom) {
        Optional<Building> building = repo.findById(buildingId);

        building.ifPresent(value -> value.removeMeetingRoom(meetingRoom));
    }
}
