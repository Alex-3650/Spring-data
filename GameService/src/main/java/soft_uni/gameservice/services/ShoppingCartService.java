package soft_uni.gameservice.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.w3c.dom.stylesheets.LinkStyle;
import soft_uni.gameservice.dtos.ShoppingCart.ShoppingCartItemDto;
import soft_uni.gameservice.dtos.ShoppingCart.ShoppingCartItemInputDto;
import soft_uni.gameservice.entities.ShoppingCartItem;

import java.util.List;

@Validated
public interface ShoppingCartService {

    ShoppingCartItemDto create(@Valid ShoppingCartItemInputDto dto);
    List<ShoppingCartItem> getForUser(@NotNull long userId);

    void clearForUser(long id);
}
