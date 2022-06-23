package cz.cvut.fel.roomreservationservice.service;

import cz.cvut.fel.roomreservationservice.enums.ReservationStatus;
import cz.cvut.fel.roomreservationservice.service.interfaces.ReservationService;
import events.InvoiceCreationFailed;
import events.InvoiceEvent;
import events.InvoicePaid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventHandlerImplTest {

    @Autowired
    private EventHandlerImpl eventHandler;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void invoiceCreationFailed() {
        InvoiceEvent invoiceCreationFailed = new InvoiceCreationFailed(1);

        eventHandler.onEvent(invoiceCreationFailed).thenRun(() -> {
            Mockito.verify(reservationService, Mockito.times(1)).deleteReservation(1);
        });
    }

    @Test
    public void invoicePaidTest() {
        InvoiceEvent invoicePaid = new InvoicePaid(1,2);

        eventHandler.onEvent(invoicePaid).thenRun(() -> {
            Mockito.verify(reservationService, Mockito.times(1)).updateReservationStatus(2, ReservationStatus.PAID);
        });
    }

}
