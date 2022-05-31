package cz.cvut.fel.invoice.service;

import cz.cvut.fel.invoice.model.Invoice;

import java.util.List;

public interface InvoiceService {

    Invoice findById(Integer ownerId, Integer id) throws Exception;

    List<Invoice> getMyInvoices(Integer ownerId);

    Invoice save(Integer ownerId, Integer reservationId, Integer price);

    Invoice findByIdAdmin(Integer id);

    List<Invoice> getUserInvoicesAdmin(Integer id);

    void deleteById(Integer id);

    void payInvoice(Integer ownerId, Integer id) throws Exception;
}
