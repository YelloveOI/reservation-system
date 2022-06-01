package events;

import lombok.Getter;
import lombok.Setter;

public class ReservationCreated extends ReservationEvent {

    @Getter
    @Setter
    public Integer userId;

    @Getter
    @Setter
    public Integer totalPrice;

    public ReservationCreated() {
    }

    public ReservationCreated(Integer reservationId, Integer userId, Integer totalPrice) {
        super(reservationId);
        this.userId = userId;
        this.totalPrice = totalPrice;
    }
}
