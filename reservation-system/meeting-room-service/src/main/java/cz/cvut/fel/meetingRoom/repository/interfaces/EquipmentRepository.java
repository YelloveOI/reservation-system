package cz.cvut.fel.meetingRoom.repository.interfaces;

import cz.cvut.fel.meetingRoom.domain.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Integer> {
}
