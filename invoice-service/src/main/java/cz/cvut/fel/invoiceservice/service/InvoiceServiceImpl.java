package cz.cvut.fel.invoiceservice.service;

import cz.cvut.fel.invoiceservice.exception.NotFoundException;
import cz.cvut.fel.invoiceservice.kafka.publishers.interfaces.InvoiceEventPublisher;
import cz.cvut.fel.invoiceservice.model.Invoice;
import cz.cvut.fel.invoiceservice.repository.InvoiceRepository;
import cz.cvut.fel.invoiceservice.service.interfaces.InvoiceService;
import events.InvoicePaid;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repo;

    private final InvoiceEventPublisher publisher;

    private final Logger logger = LoggerFactory.getLogger(EventHandlerImpl.class);

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository repo, InvoiceEventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @Override
    public Invoice findById(@NotNull Integer ownerId, @NotNull Integer id) throws Exception {
        Optional<Invoice> result = repo.findById(id);
        if (result.isPresent()) {
            Invoice invoice = result.get();
            if (invoice.getOwnerId().equals(ownerId)) {
                return invoice;
            } else {
                throw new Exception("This invoice doesn't belong to you.");
            }
        } else {
            throw NotFoundException.create(Invoice.class.getName(), id);
        }
    }

    @Override
    public List<Invoice> getMyInvoices(@NotNull Integer ownerId) {
        return repo.findAllByOwnerIdAndRemovedIsFalse(ownerId);
    }

    @Override
    public Invoice save(@NotNull Integer ownerId, @NotNull Integer reservationId, @NotNull Integer price) throws IllegalArgumentException {
        logger.info("Creating new Invoice");

        if(price < 0) throw new IllegalArgumentException();

        return repo.save(new Invoice(ownerId, reservationId, price));
    }

    @Override
    public void payInvoice(@NotNull Integer ownerId, @NotNull Integer id) throws Exception {
        Invoice invoice = findById(ownerId, id);
        invoice.setPaid(true);

        repo.save(invoice);

        publisher.send(new InvoicePaid(id, invoice.getReservationId()));
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
    public List<Invoice> getUserInvoicesAdmin(@NotNull Integer id) {
        return repo.findAllByOwnerId(id);
    }

    @Override
    public void deleteById(@NotNull Integer id) {
        Optional<Invoice> toDelete = repo.findById(id);
        if (toDelete.isPresent()) {
            Invoice invoice = toDelete.get();
            invoice.setRemoved(true);

            repo.save(toDelete.get());
        } else {
            throw NotFoundException.create(Invoice.class.getName(), id);
        }
    }

    @Override
    public void deleteAllByReservationId(Integer reservationId) {
        repo.findAllByReservationId(reservationId).forEach(v -> deleteById(v.getId()));
    }
}
