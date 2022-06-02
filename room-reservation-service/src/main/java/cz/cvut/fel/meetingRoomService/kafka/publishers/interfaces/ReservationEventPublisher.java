package cz.cvut.fel.meetingRoomService.kafka.publishers.interfaces;

import events.ReservationEvent;

public interface ReservationEventPublisher {

    void send(ReservationEvent event);

}
