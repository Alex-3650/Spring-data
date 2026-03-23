package soft_uni.gameservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import soft_uni.gameservice.dtos.Game.GameInputDto;
import soft_uni.gameservice.dtos.Game.GameDto;
import soft_uni.gameservice.dtos.Order.OrderDto;
import soft_uni.gameservice.dtos.Order.OrderInputDto;
import soft_uni.gameservice.dtos.ShoppingCart.ShoppingCartItemDto;
import soft_uni.gameservice.dtos.ShoppingCart.ShoppingCartItemInputDto;
import soft_uni.gameservice.dtos.User.UserDto;
import soft_uni.gameservice.dtos.User.UserLoginDto;
import soft_uni.gameservice.dtos.User.UserRegisterDto;
import soft_uni.gameservice.services.GameServiceImpl;
import soft_uni.gameservice.services.OrderService;
import soft_uni.gameservice.services.ShoppingCartServiceImpl;
import soft_uni.gameservice.services.UserServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

@Component
public class Runner implements CommandLineRunner {
    private final UserServiceImpl userService;
    private final GameServiceImpl gameService;
    private final ShoppingCartServiceImpl shoppingCartService;
    private final OrderService orderService;
    private final BCryptPasswordEncoder encoder;


    private UserDto currentUser;

    @Autowired
    public Runner(UserServiceImpl userService, GameServiceImpl gameService, ShoppingCartServiceImpl shoppingCartService, BCryptPasswordEncoder bCryptPasswordEncoder, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.shoppingCartService = shoppingCartService;
        this.encoder = bCryptPasswordEncoder;
        this.orderService = orderService;
    }


    @Override
    public void run(String... args) throws Exception {
      ensureFirstAdmin();

      Scanner scanner = new Scanner(System.in);
      Map<String, Function<String[],String>> commands = new HashMap<>();

      //TODO: Add command to list the items in the current user shopping cart
      commands.put("RegisterUser",this::register);
      commands.put("LoginUser",this::login);
      commands.put("WhoAmI",this::whoAmI);
      commands.put("Logout",this::logout);
      commands.put("AddGame",this::addGame);
      commands.put("AllGames",this::listAllGames);
      //TODO: ADD METHODS FOR UPDATE QUANTITY AND DELETE THE GAME FROM THE SHOPPING CART
      commands.put("BuyGame",this::buyGame);
      //TODO: IMPLEMENT DTO FOR THIS METHOD
      commands.put("Purchase",this::purchase);

      String input = scanner.nextLine();
      while (!input.equals("Exit")) {
          String [] data = input.split("\\|");
          String command = data[0];
          Function<String[],String> function = commands.get(command);
          String output = execute(function, data);
          System.out.println(output);
          input = scanner.nextLine();
      }

    }

    private String purchase(String[] data) {
     this.ensureAuthenticated();
     OrderInputDto orderInputDto = new OrderInputDto(this.currentUser.getId());
        OrderDto orderDto = this.orderService.create(orderInputDto);

        StringBuilder sb = new StringBuilder("Order with ID "+orderDto.getId()+" was created successfully.");
        sb.append("\n Order items:");
        orderDto.getOrderItems().forEach(orderItemDto -> {
            sb.append("\n ID:%d  Title:\"%s\" Quantity:%d".formatted(orderItemDto.getId(),
                                                orderItemDto.getGame().getTitle(),
                                                orderItemDto.getQuantity()));});
        return sb.toString();
    }

    private String buyGame(String[] data  ) {
        this.ensureAuthenticated();

        long gameId = Long.parseLong(data[1]);
        long userId = this.currentUser.getId();
        int quantity = Integer.parseInt(data[2]);
        ShoppingCartItemInputDto inputDto = new ShoppingCartItemInputDto(userId, gameId, quantity);
        ShoppingCartItemDto dto = this.shoppingCartService.create(inputDto);

        return "Game \"%s\" was added to your shopping cart.Shopping cart item ID: %d".formatted(dto.getGame().getTitle(), dto.getId());
    }


    private String whoAmI(String[] data) {
       ensureAuthenticated();
       return "You are logged in as \"%s\" with ID %d"
               .formatted(this.currentUser.getEmail(), this.currentUser.getId()) ;
    }


    private String register(String []data) {
        ensureAnonymous();
        UserRegisterDto userRegisterDto = new UserRegisterDto(data[1],data[2],data[3]);
        UserDto createdUser = this.userService.register(userRegisterDto);

        return "User \"%s\" with ID %d was registered successfully".formatted(createdUser.getEmail(),createdUser.getId());
    }

    private String login(String []data) {
        ensureAnonymous();
        UserLoginDto userLoginDto = new UserLoginDto(data[1],data[2]);
        UserDto loginUser = this.userService.login(userLoginDto);
        if (loginUser == null) {
            return "Invalid username or password";
        }
        this.currentUser = loginUser;
        return "User \"%s\" with ID %d was logged in successfully".formatted(loginUser.getEmail(),loginUser.getId());
    }

    private String logout(String[] data) {
        this.ensureAuthenticated();
        this.currentUser = null;
        return "You logged out successfully";
    }

    private String addGame(String[] data) {
     this.ensureAdmin();

     GameInputDto gameInputDto = new GameInputDto(data[1],data[2],data[3], new BigDecimal(data[4]),data[5]);
     GameDto gameDto = this.gameService.create(gameInputDto);

     return "Game %s with ID %d was created successfully!".formatted(gameDto.getTitle(), gameDto.getId());
    }
    private String listAllGames(String[] strings) {
        this.ensureAuthenticated();
        StringBuilder sb = new StringBuilder("All games:");
        List<GameDto> allGames = this.gameService.all();

        allGames.forEach(gameDto -> {
            sb.append("\nGame \"%s\" with ID %d and price %.2f$".formatted(gameDto.getTitle(), gameDto.getId(), gameDto.getPrice()));
        });
        return sb.toString();
    }


    //HELPER METHODS
    private void ensureAuthenticated(){
        if (this.currentUser == null)
            throw new IllegalArgumentException("You are not logged in.Please, log in first.");
    }
    private void ensureAdmin(){
        this.ensureAuthenticated();
        if (!this.currentUser.getAdmin()) {
            throw new IllegalArgumentException("You are not admin.Please,contact your administrator.");
        }
    }

    private void ensureAnonymous(){
          if (this.currentUser != null)
              throw new IllegalArgumentException("You are already logged in.Please,log out first.");
    }

    private static String execute(Function<String[], String> function, String[] data) {

        if (function == null) {
            return "Invalid command";
        } else {
            try {
                return function.apply(data);
            } catch (Exception e) {
                return "ERROR!!! " + e.getMessage();
            }
        }
    }

    private void ensureFirstAdmin() {
        UserRegisterDto admin = new UserRegisterDto(
                "gameStore@gmail.com",
                "12345",
                "Ivan Peshev");

      UserDto userDto = userService.ensureAdmin(admin);

        if (userDto!=null) {
            System.out.printf("Admin registered with ID %d\n",userDto.getId());
        }else {
            System.out.println("Admin have already registered ");
        }
    }
}
