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

    /**
     * Checks if an invoice having such id exists and is not removed, if so, checks if the invoice belongs to the user
     * and if so, returns it. Otherwise throws an Exception.
     * @param ownerId Users id.
     * @param id Invoice id.
     * @return Corresponding invoice.
     * @throws Exception NotFoundException for not found and Exception with description if not owned by the user.
     */
    @Override
    public Invoice findById(@NotNull Integer ownerId, @NotNull Integer id) throws Exception {
        Optional<Invoice> result = repo.findByIdAndRemovedIsFalse(id);
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

    /**
     * Returns all of the valid invoices of the user. Excluding removed invoices.
     * @param ownerId Users id.
     * @return All the users valid invoices.
     */
    @Override
    public List<Invoice> getMyInvoices(@NotNull Integer ownerId) {
        return repo.findAllByOwnerIdAndRemovedIsFalse(ownerId);
    }

    /**
     * Stores a new invoice. If the price value is invalid, throws IllegalArgumentException.
     * @param ownerId Users id.
     * @param reservationId Corresponding reservation id.
     * @param price Price of the reservation. (above 0)
     * @return Returns newly created invoice.
     * @throws IllegalArgumentException Thrown if the price value is invalid.
     */
    @Override
    public Invoice save(@NotNull Integer ownerId, @NotNull Integer reservationId, @NotNull Integer price) throws IllegalArgumentException {
        logger.info("Creating new Invoice");

        if(price < 0) throw new IllegalArgumentException();

        return repo.save(new Invoice(ownerId, reservationId, price));
    }

    /**
     * Marks the invoice as paid if the invoice exists.
     * @param ownerId Users id.
     * @param id Invoice id.
     * @throws Exception Invoice may not exist.
     */
    @Override
    public void payInvoice(@NotNull Integer ownerId, @NotNull Integer id) throws Exception {
        Invoice invoice = findById(ownerId, id);
        invoice.setPaid(true);

        repo.save(invoice);

        publisher.send(new InvoicePaid(id, invoice.getReservationId()));
    }

    /**
     * Returns and existing invoice, even deleted.
     * @param id Id of the invoice.
     * @return Returns the found invoice.
     */
    @Override
    public Invoice findByIdAdmin(@NotNull Integer id) {
        Optional<Invoice> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw NotFoundException.create(Invoice.class.getName(), id);
        }
    }

    /**
     * Returns all the owners existing invoices, even the deleted ones.
     * @param id Id of the owner.
     * @return Returns a list of found invoices.
     */
    @Override
    public List<Invoice> getUserInvoicesAdmin(@NotNull Integer id) {
        return repo.findAllByOwnerId(id);
    }

    /**
     * Marks an invoice as removed if exists.
     * @param id Id of the invoice.
     */
    @Override
    public void removeById(@NotNull Integer id) {
        Optional<Invoice> toDelete = repo.findById(id);
        if (toDelete.isPresent()) {
            Invoice invoice = toDelete.get();
            invoice.setRemoved(true);

            repo.save(toDelete.get());
        } else {
            throw NotFoundException.create(Invoice.class.getName(), id);
        }
    }

    /**
     * Deletes all the invoices corresponding to a particular reservation.
     * @param reservationId Id of the (cancelled) reservation.
     */
    @Override
    public void deleteAllByReservationId(Integer reservationId) {
        repo.findAllByReservationId(reservationId).forEach(v -> removeById(v.getId()));
    }
}
