package cz.cvut.fel.meetingRoom.repository.interfaces;

import cz.cvut.fel.meetingRoom.domain.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
}
