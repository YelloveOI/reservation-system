package events;

public class ReservationCancelled extends ReservationEvent {
    public ReservationCancelled(Integer reservationId) {
        super(reservationId);
    }
    public ReservationCancelled() {
    }
}
