package cz.cvut.fel.invoice.service;

import cz.cvut.fel.invoice.model.Invoice;

import java.util.List;

public interface InvoiceService {

    Invoice findById(Integer id) throws Exception;

    List<Invoice> getMyInvoices();

    void save(Invoice invoice) throws Exception;

    Invoice findByIdAdmin(Integer id);

    List<Invoice> getUserInvoicesAdmin(Integer id);

    /*void deleteById(Integer id);*/
}
