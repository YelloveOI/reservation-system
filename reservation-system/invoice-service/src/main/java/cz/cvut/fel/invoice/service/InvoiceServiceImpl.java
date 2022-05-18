package cz.cvut.fel.invoice.service;

import com.sun.istack.NotNull;
import cz.cvut.fel.invoice.exception.NotFoundException;
import cz.cvut.fel.invoice.model.Invoice;
import cz.cvut.fel.invoice.repo.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepo repo;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepo repo) {
        this.repo = repo;
    }

    @Override
    public Invoice findById(@NotNull Integer id) throws Exception {
        Optional<Invoice> result = repo.findById(id);
        if (result.isPresent()) {
            Invoice invoice = result.get();
            if (!invoice.isDeleted() && invoice.getOwner().getId().equals(SecurityUtils.getCurrentUser().getId())) {
                return invoice;
            } else {
                throw new Exception("This deck doesn't belong to you.");
            }
        } else {
            throw NotFoundException.create(Invoice.class.getName(), id);
        }
    }

    @Override
    public List<Invoice> getMyInvoices() {
        return repo.findAllByOwnerIdAndDeletedIsFalse(SecurityUtils.getCurrentUser().getId());
    }

    @Override
    public void save(@NotNull Invoice invoice) throws Exception {
        if (invoice.getOwner() != null) {
            if (!invoice.getOwner().getId().equals(SecurityUtils.getCurrentUser().getId())) {
                throw new Exception("You can't save someone else's invoice.");
            }
        }
        invoice.setOwner(SecurityUtils.getCurrentUser());
        repo.save(invoice);
    }

    /* ADMIN **/

    @Override
    public Invoice findByIdAdmin(@NotNull Integer id) {
        Optional<Invoice> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw NotFoundException.create(Invoice.class.getName(), id);
        }
    }

    @Override
    public List<Invoice> getUserInvoicesAdmin(Integer id) {
        return repo.findAllByOwnerId(id);
    }

    @Override
    public void deleteById(@NotNull Integer id) {
        Optional<Invoice> toDelete = repo.findById(id);
        if (toDelete.isPresent()) {
            Invoice invoice = toDelete.get();
            invoice.setDeleted(true);

            repo.save(toDelete.get());
        } else {
            throw NotFoundException.create(Invoice.class.getName(), id);
        }
    }
}