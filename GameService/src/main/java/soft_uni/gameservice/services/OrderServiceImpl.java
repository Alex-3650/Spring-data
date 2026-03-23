package soft_uni.gameservice.services;

import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft_uni.gameservice.dtos.Order.OrderDto;
import soft_uni.gameservice.dtos.Order.OrderInputDto;
import soft_uni.gameservice.entities.Order;
import soft_uni.gameservice.entities.OrderItem;
import soft_uni.gameservice.entities.ShoppingCartItem;
import soft_uni.gameservice.entities.User;
import soft_uni.gameservice.repositories.OrderRepository;
import soft_uni.gameservice.repositories.UserRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final ModelMapper modelMapper;


    public OrderServiceImpl(UserService userService, OrderRepository orderRepository, ShoppingCartService shoppingCartService, ModelMapper modelMapper) {
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public OrderDto create(OrderInputDto orderDto) {
        User user = userService.getRequired(orderDto.getUserId());

        //Get shopping cart items
        List<ShoppingCartItem> items = this.shoppingCartService.getForUser(user.getId());
        if (items.isEmpty()) throw new IllegalArgumentException("No shopping cart items found");

        //Create order
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setUser(user);

        Set<OrderItem> allOrderItems = new HashSet<>();
        for (ShoppingCartItem item : items) {
            OrderItem current = new OrderItem();
            current.setGame(item.getGame());
            current.setQuantity(item.getQuantity());
            current.setPrice(item.getGame().getPrice());
            current.setOrder(order);
            allOrderItems.add(current);
        }
        order.setOrderItems(allOrderItems);

        this.orderRepository.save(order);

        //Truncate shopping cart
        this.shoppingCartService.clearForUser(user.getId());

       return modelMapper.map(order, OrderDto.class);

    }
}
