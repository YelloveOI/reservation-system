package events;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public abstract class ReservationEvent {

    @Getter
    @Setter
    public String eventId;
    @Getter
    @Setter
    public Long timestamp;
    @Getter
    @Setter
    public Integer reservationId;

    public ReservationEvent() {
    }

    public ReservationEvent(Integer reservationId) {
        this(UUID.randomUUID().toString().substring(0, 7), System.currentTimeMillis(), reservationId);
    }

    public ReservationEvent(String eventId, Long timestamp, Integer reservationId) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.reservationId = reservationId;
    }
}
