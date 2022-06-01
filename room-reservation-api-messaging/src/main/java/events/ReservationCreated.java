package events;

import lombok.Getter;

public class ReservationCreated extends ReservationEvent {

    @Getter
    private final Integer userId;

    @Getter
    private final Integer totalPrice;

    public ReservationCreated(Integer reservationId, Integer userId, Integer totalPrice) {
        super(reservationId);
        this.userId = userId;
        this.totalPrice = totalPrice;
    }
}
