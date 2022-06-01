package events;

import lombok.Getter;

import java.util.UUID;

public abstract class ReservationEvent {

    @Getter
    private final String eventId;
    @Getter
    private final Long timestamp;
    @Getter
    private final Integer reservationId;

    public ReservationEvent(Integer reservationId) {
        this(UUID.randomUUID().toString().substring(0, 7), System.currentTimeMillis(), reservationId);
    }

    public ReservationEvent(String eventId, Long timestamp, Integer reservationId) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.reservationId = reservationId;
    }
}
