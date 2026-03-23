package soft_uni.gameservice.dtos.ShoppingCart;

import soft_uni.gameservice.dtos.Game.GameDto;
import soft_uni.gameservice.dtos.Game.GameInputDto;
import soft_uni.gameservice.dtos.User.UserDto;

public class ShoppingCartItemDto {
    private long id;
    private UserDto user;
    private GameDto game;
    private Integer quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
