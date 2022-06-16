package cz.cvut.fel.invoice.repository;

import cz.cvut.fel.invoice.model.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {

    @Query("select i from Invoice i where i.ownerId = ?1 and i.isRemoved = false ORDER BY i.creationDate, i.creationTime")
    List<Invoice> findAllByOwnerIdAndRemovedIsFalse(Integer id);

    List<Invoice> findAllByOwnerId(Integer id);

    List<Invoice> findAllByReservationId(Integer reservationId);

}

