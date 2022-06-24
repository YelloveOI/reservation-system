package cz.cvut.fel.invoiceservice.service;

import cz.cvut.fel.invoiceservice.service.interfaces.InvoiceService;
import events.*;
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
    private InvoiceService invoiceService;

    @Test
    public void invoiceCancelled() {
        Integer reservationId = 240;

        ReservationEvent reservationCancelled = new ReservationCancelled(reservationId);

        eventHandler.onEvent(reservationCancelled).thenRun(() ->
                Mockito.verify(invoiceService,
                        Mockito.times(1)).deleteAllByReservationId(reservationId));
    }

    @Test
    public void invoiceCreatedTest() {
        Integer reservationId = 517;
        Integer userId = 4;
        Integer amount = 1000;

        ReservationCreated reservationCreated = new ReservationCreated(reservationId, userId, amount);

        eventHandler.onEvent(reservationCreated).thenRun(() ->
                Mockito.verify(invoiceService,
                        Mockito.times(1)).save(userId, reservationId, amount));
    }
}
