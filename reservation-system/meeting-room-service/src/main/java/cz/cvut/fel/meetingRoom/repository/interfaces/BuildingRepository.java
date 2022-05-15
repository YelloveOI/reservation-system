package cz.cvut.fel.meetingRoom.repository.interfaces;

import cz.cvut.fel.meetingRoom.domain.Building;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends CrudRepository<Building, Integer> {
}
