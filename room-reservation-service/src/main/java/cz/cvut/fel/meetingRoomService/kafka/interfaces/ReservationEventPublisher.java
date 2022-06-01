package cz.cvut.fel.meetingRoomService.kafka.interfaces;

import events.ReservationEvent;

public interface ReservationEventPublisher {

    void send(ReservationEvent event);

}
