package soft_uni.gameservice.dtos.ShoppingCart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import soft_uni.gameservice.entities.ShoppingCartItem;

public class ShoppingCartItemInputDto {
    @NotNull
    private final long userId;
    @NotNull
    private final long gameId;
    @NotNull
    @Positive
    private final Integer quantity;

    public ShoppingCartItemInputDto(long userId, long gameId, Integer quantity) {
        this.userId = userId;
        this.gameId = gameId;
        this.quantity = quantity;
    }

    public long getUserId() {
        return userId;
    }

    public long getGameId() {
        return gameId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
