package soft_uni.gameservice.dtos.Order;

import jakarta.validation.constraints.NotNull;
import soft_uni.gameservice.entities.OrderItem;

import java.util.HashSet;
import java.util.Set;

public class OrderDto {

    @NotNull
    private  long id;
    @NotNull
    private  Set<OrderItem> orderItems ;



    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
