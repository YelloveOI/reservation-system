package cz.cvut.fel.invoiceservice.service;

import cz.cvut.fel.invoiceservice.exception.NotFoundException;
import cz.cvut.fel.invoiceservice.kafka.publishers.interfaces.InvoiceEventPublisher;
import cz.cvut.fel.invoiceservice.model.Invoice;
import cz.cvut.fel.invoiceservice.repository.InvoiceRepository;
import cz.cvut.fel.invoiceservice.service.interfaces.InvoiceService;
import events.InvoicePaid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class InvoiceServiceImplTest {

    @Autowired
    private InvoiceRepository repo;

    @Autowired
    private InvoiceService invoiceService;

    @MockBean
    InvoiceEventPublisher publisher;

    @Test
    public void findByIdCorrect() throws Exception {
        Integer ownerId = 101;
        repo.save(new Invoice(5, 180847, 2000));
        Invoice expectedInvoice = new Invoice(ownerId, 90847, 700);
        repo.save(expectedInvoice);
        Integer expectedInvoiceId = expectedInvoice.getId();
        repo.save(new Invoice(ownerId, 580847, 100));
        repo.save(new Invoice(40, 1847, 2500));

        Invoice result = invoiceService.findById(ownerId, expectedInvoiceId);

        assertEquals(expectedInvoiceId, result.getId());
    }

    @Test
    public void findByIdRemoved() {
        Integer ownerId = 102;
        Invoice expectedInvoice = new Invoice(101, 90847, 700);
        repo.save(new Invoice(ownerId, 180847, 2000));
        repo.save(new Invoice(101, 580847, 100));
        repo.save(expectedInvoice);
        Integer expectedInvoiceId = expectedInvoice.getId();
        invoiceService.removeById(expectedInvoiceId);
        repo.save(new Invoice(ownerId, 1847, 2500));

        Assertions.assertThrows(NotFoundException.class, () -> invoiceService.findById(ownerId, expectedInvoiceId));
    }

    @Test
    public void findByIdIllegalAccess() {
        Integer ownerId = 102;
        Invoice expectedInvoice = new Invoice(101, 90847, 700);
        repo.save(new Invoice(ownerId, 180847, 2000));
        repo.save(new Invoice(101, 580847, 100));
        repo.save(expectedInvoice);
        Integer expectedInvoiceId = expectedInvoice.getId();
        repo.save(new Invoice(ownerId, 1847, 2500));

        Assertions.assertThrows(Exception.class, () -> invoiceService.findById(ownerId, expectedInvoiceId));
    }

    @Test
    public void getMyInvoicesDoesntShowRemovedOnes() {
        Integer ownerId = 101;
        Invoice tmpInvoice;
        List<Invoice> expectedInvoices = new ArrayList<>();
        tmpInvoice = new Invoice(ownerId, 90847, 700);
        expectedInvoices.add(tmpInvoice);
        repo.save(tmpInvoice);
        repo.save(new Invoice(5, 180847, 2000));
        tmpInvoice = new Invoice(ownerId, 580847, 100);
        expectedInvoices.add(tmpInvoice);
        repo.save(tmpInvoice);
        tmpInvoice = new Invoice(ownerId, 849, 1050);
        repo.save(tmpInvoice);
        invoiceService.removeById(tmpInvoice.getId());
        repo.save(new Invoice(40, 1847, 2500));

        List<Invoice> result = invoiceService.getMyInvoices(ownerId);

        assertEquals(expectedInvoices, result);
    }

    @Test
    public void payInvoicePaid() throws Exception {
        Integer ownerId = 101;
        repo.save(new Invoice(5, 180847, 2000));
        Invoice expectedInvoice = new Invoice(ownerId, 90847, 700);
        repo.save(expectedInvoice);
        Integer expectedInvoiceId = expectedInvoice.getId();
        repo.save(new Invoice(ownerId, 580847, 100));

        invoiceService.payInvoice(ownerId, expectedInvoiceId);

        assertTrue(expectedInvoice.isPaid());
        Mockito.verify(publisher, Mockito.times(1)).send(
                ArgumentMatchers.argThat(e -> e instanceof InvoicePaid && e.invoiceId.equals(expectedInvoiceId))
        );
    }

    @Test
    public void payInvoiceInvalidUser() {
        Integer ownerId = 101;
        repo.save(new Invoice(5, 180847, 2000));
        Invoice expectedInvoice = new Invoice(ownerId, 90847, 700);
        repo.save(expectedInvoice);
        Integer expectedInvoiceId = expectedInvoice.getId();
        repo.save(new Invoice(ownerId, 580847, 100));

        Assertions.assertThrows(Exception.class, () -> invoiceService.payInvoice(102, expectedInvoiceId));

    }

    @Test
    public void findByIdAdminCorrect() {
        Integer ownerId = 101;
        repo.save(new Invoice(ownerId, 180847, 2000));
        Invoice expectedInvoice = new Invoice(102, 90847, 700);
        repo.save(expectedInvoice);
        Integer expectedInvoiceId = expectedInvoice.getId();
        repo.save(new Invoice(102, 580847, 100));
        repo.save(new Invoice(40, 1847, 2500));

        Invoice result = invoiceService.findByIdAdmin(expectedInvoiceId);

        assertEquals(expectedInvoiceId, result.getId());
    }

    @Test
    public void findByIdAdminDoesntExist() {
        Integer ownerId = 101;
        repo.save(new Invoice(102, 90847, 700));
        repo.save(new Invoice(ownerId, ownerId, 2000));
        repo.save(new Invoice(102, 580847, 100));
        repo.save(new Invoice(ownerId, 1847, 2500));

        Assertions.assertThrows(NotFoundException.class, () -> invoiceService.findByIdAdmin(558982954));
    }

    @Test
    public void getUserInvoicesAdminShowsAlsoRemovedOnes() {
        Integer ownerId = 101;
        Invoice tmpInvoice;
        List<Invoice> expectedInvoices = new ArrayList<>();
        tmpInvoice = new Invoice(ownerId, 90847, 700);
        expectedInvoices.add(tmpInvoice);
        repo.save(tmpInvoice);
        repo.save(new Invoice(5, 180847, 2000));
        tmpInvoice = new Invoice(ownerId, 580847, 100);
        expectedInvoices.add(tmpInvoice);
        repo.save(tmpInvoice);
        tmpInvoice = new Invoice(ownerId, 849, 1050);
        expectedInvoices.add(tmpInvoice);
        repo.save(tmpInvoice);
        invoiceService.removeById(tmpInvoice.getId());
        repo.save(new Invoice(40, 1847, 2500));

        List<Invoice> result = invoiceService.getUserInvoicesAdmin(ownerId);

        assertEquals(expectedInvoices, result);
    }

    @Test
    public void removeByIdInvoiceStillExists() {
        Integer ownerId = 101;
        repo.save(new Invoice(5, 180847, 2000));
        Invoice expectedInvoice = new Invoice(ownerId, 90847, 700);
        repo.save(expectedInvoice);
        Integer expectedInvoiceId = expectedInvoice.getId();
        repo.save(new Invoice(ownerId, 580847, 100));
        repo.save(new Invoice(40, 1847, 2500));

        invoiceService.removeById(expectedInvoiceId);
        Assertions.assertThrows(Exception.class, () -> invoiceService.findById(ownerId, expectedInvoiceId));
        Invoice result = invoiceService.findByIdAdmin(expectedInvoiceId);

        assertEquals(expectedInvoiceId, result.getId());
        assertTrue(result.isRemoved());
    }

    @Test
    public void deleteAllByReservationIdCorrect() {
        Integer ownerId = 101;
        Integer reservationId = 1;
        List<Invoice> expectedInvoices = new ArrayList<>();
        repo.save(new Invoice(ownerId, reservationId, 2000));
        repo.save(new Invoice(102, 90847, 700));
        repo.save(new Invoice(ownerId, reservationId, 100));
        Invoice tmpInvoice = new Invoice(ownerId, 2, 2500);
        repo.save(tmpInvoice);
        expectedInvoices.add(tmpInvoice);
        repo.save(new Invoice(ownerId, reservationId, 500));

        invoiceService.deleteAllByReservationId(reservationId);
        List<Invoice> result = invoiceService.getMyInvoices(ownerId);

        assertEquals(expectedInvoices, result);
    }
}
