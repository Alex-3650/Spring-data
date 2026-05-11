package soft_uni.gameservice.dtos.Order;

import jakarta.validation.constraints.NotNull;

public class OrderInputDto {

    @NotNull
    private final long userId;

    public OrderInputDto( long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
