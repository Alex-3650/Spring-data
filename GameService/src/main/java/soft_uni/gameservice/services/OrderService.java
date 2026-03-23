package soft_uni.gameservice.services;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import soft_uni.gameservice.dtos.Order.OrderDto;
import soft_uni.gameservice.dtos.Order.OrderInputDto;

@Validated
public interface OrderService {
    OrderDto create(@Valid OrderInputDto orderDto);
}
