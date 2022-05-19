package cz.cvut.fel.invoice.rest;

import cz.cvut.fel.invoice.model.Invoice;
import cz.cvut.fel.invoice.rest.util.RestUtils;
import cz.cvut.fel.invoice.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceController.class);

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * Returns given invoice if authored to do so.
     * @param id ID of invoice
     * @return invoice with given ID
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/{id}")
    public Invoice getInvoice(@PathVariable int id) {
        Invoice invoice;
        try {
            invoice = invoiceService.findById(id);
        } catch (Exception e) {
            LOG.warn("Invoice could not be retrieved! {}", e.getMessage());
            return null;
        }
        LOG.debug("Invoice was found.");
        return invoice;
    }

    /**
     * Get current user's invoices.
     * @return Current user's invoices
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/my")
    public List<Invoice> getMyInvoices() {
        List<Invoice> invoices;
        try {
            invoices = invoiceService.getMyInvoices();
        } catch (Exception e) {
            LOG.warn("Invoices could not be retrieved! {}", e.getMessage());
            return null;
        }
        LOG.debug("Invoices were found.");
        return invoices;
    }

    /**
     * Assigns new invoice to current user by receiving price.
     * @param price price of the meeting room
     * @return Ok/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createInvoice(@RequestBody int price) {
        Invoice invoice;
        try {
            invoice = invoiceService.save(price);
        } catch (Exception e) {
            LOG.warn("Invoice could not be created! {}", e.getMessage());
            return ResponseEntity.badRequest().body("WRONG");
        }
        LOG.debug("Invoice ID \"{}\" has been created.", invoice.getId());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", invoice.getId());
        return ResponseEntity.ok().headers(headers).body("OK");
    }

    /**
     * Paying for given invoice.
     * @param id ID of given invoice.
     * @return Ok/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> payForInvoice(@RequestBody int id) {
        try {
            invoiceService.payInvoice(id);
        } catch (Exception e) {
            LOG.warn("Invoice could not be paid for! {}", e.getMessage());
            return ResponseEntity.badRequest().body("WRONG");
        }
        LOG.debug("Invoice ID \"{}\" was paid for.", id);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", id);
        return ResponseEntity.ok().headers(headers).body("OK");
    }

    /* ADMIN **/

    /**
     * Return any given invoice to admin.
     * @param id ID of invoice
     * @return invoice with given ID
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/{id}")
    public Invoice getDeckAdmin(@PathVariable int id) {
        Invoice invoice;
        try {
            invoice = invoiceService.findByIdAdmin(id);
        } catch (Exception e) {
            LOG.warn("Invoice could not be retrieved! {}", e.getMessage());
            return null;
        }
        LOG.debug("Invoice was found.");
        return invoice;
    }

    /**
     * Get given user's invoices to an admin.
     * @return Current user's invoices
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/{id}")
    public List<Invoice> getUserInvoicesAdmin(@PathVariable int id) {
        List<Invoice> invoices;
        try {
            invoices = invoiceService.getUserInvoicesAdmin(id);
        } catch (Exception e) {
            LOG.warn("Invoices could not be retrieved! {}", e.getMessage());
            return null;
        }
        LOG.debug("Invoices were found.");
        return invoices;
    }

    /**
     * Delete given invoice by admin.
     * @param id ID of given invoice
     * @return Bad Request/Accepted
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/deletion/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> deleteInvoice(@PathVariable int id) {
        try {
            invoiceService.deleteById(id);
        } catch (Exception e) {
            LOG.warn("Invoice could not be deleted! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Invoice ID \"{}\" has been deleted.", id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
