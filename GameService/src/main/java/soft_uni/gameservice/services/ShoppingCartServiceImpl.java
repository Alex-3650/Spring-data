package soft_uni.gameservice.services;

import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import soft_uni.gameservice.dtos.ShoppingCart.ShoppingCartItemDto;
import soft_uni.gameservice.dtos.ShoppingCart.ShoppingCartItemInputDto;
import soft_uni.gameservice.entities.Game;
import soft_uni.gameservice.entities.ShoppingCartItem;
import soft_uni.gameservice.entities.User;
import soft_uni.gameservice.repositories.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ModelMapper modelMapper;
    private final GameService gameService;
    private final UserService userService;


    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ModelMapper modelMapper, GameService gameService, UserService userService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.modelMapper = modelMapper;
        this.gameService = gameService;
        this.userService = userService;
    }

    @Override
    public ShoppingCartItemDto create(ShoppingCartItemInputDto dto) {
        Game game = gameService.getRequired(dto.getGameId());
        User user = userService.getRequired(dto.getUserId());
        ShoppingCartItem item = new ShoppingCartItem();
        item.setGame(game);
        item.setUser(user);
        item.setQuantity(dto.getQuantity());

        this.shoppingCartRepository.save(item);
        return modelMapper.map(item, ShoppingCartItemDto.class);
    }

    @Override
    public List<ShoppingCartItem> getForUser(long userId) {
        return this.shoppingCartRepository.findAllByUserId(userId);
    }

    @Override
    @Modifying
    public void clearForUser(long userId) {
         this.shoppingCartRepository.removeAllByUserId(userId);
    }
}
