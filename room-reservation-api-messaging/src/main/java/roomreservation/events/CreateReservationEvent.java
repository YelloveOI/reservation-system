package roomreservation.events;

import lombok.Getter;
import lombok.Setter;

public class CreateReservationEvent {

    @Getter
    @Setter
    public Integer userId;

    @Getter
    @Setter
    public Integer reservationId;

    @Getter
    @Setter
    public Integer totalPrice;

    public CreateReservationEvent(Integer userId, Integer reservationId, Integer totalPrice) {
        this.userId = userId;
        this.reservationId = reservationId;
        this.totalPrice = totalPrice;
    }

    public CreateReservationEvent() {
    }
}
