package cz.cvut.fel.roomreservationservice.kafka.publishers.interfaces;

import events.ReservationEvent;

public interface ReservationEventPublisher {

    void send(ReservationEvent event);

}
