package cz.cvut.fel.invoice.repository;

import cz.cvut.fel.invoice.model.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {

    List<Invoice> findAllByOwnerIdAndDeletedIsFalse(Integer id);

    List<Invoice> findAllByOwnerId(Integer id);
}

