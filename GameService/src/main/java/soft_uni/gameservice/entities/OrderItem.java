package soft_uni.gameservice.entities;

import jakarta.persistence.*;
import org.hibernate.id.IntegralDataTypeHolder;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items",uniqueConstraints =  @UniqueConstraint(columnNames = { "order_id","game_id" }))
public class OrderItem extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "game_id",nullable = false)
    private Game game;

    @Column(name ="quantity",nullable = false)
    private Integer quantity;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
