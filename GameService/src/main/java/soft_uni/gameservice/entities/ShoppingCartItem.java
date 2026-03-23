package soft_uni.gameservice.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "shopping_cart",uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","game_id"}))
public class ShoppingCartItem extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id",nullable = false)
    private Game game;

    @Column(nullable = false)
    private Integer quantity;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
