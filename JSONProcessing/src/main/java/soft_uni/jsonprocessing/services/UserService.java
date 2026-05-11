package soft_uni.jsonprocessing.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft_uni.jsonprocessing.dtos.UserImportDto;
import soft_uni.jsonprocessing.dtos.UserWithSoldProductsDto;
import soft_uni.jsonprocessing.entities.Product;
import soft_uni.jsonprocessing.entities.User;
import soft_uni.jsonprocessing.repository.ProductRepository;
import soft_uni.jsonprocessing.repository.UserRepository;
import tools.jackson.databind.introspect.DefaultAccessorNamingStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
//private final UserRepository userRepository;
private final UserRepository userRepository;
private final ProductRepository productRepository;
private final Gson gson;
private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ProductRepository productRepository, Gson gson, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    public void getAllWithAtLeastOneSoldProduct() {
        List<User> users = this.userRepository.findAllWithListedProducts();
        System.out.println();
    }
    @Transactional
    public void getAllWithSoldProducts() {
        List<User> users = this.userRepository.findWithSoldProductsOrderByLastName();
        for (User user : users) {
            List<Product> products = this.productRepository.findBySellerAndBuyerIsNotNull(user);
            user.setSoldProducts(products);
        }
        UserWithSoldProductsDto[] usersWithSoldItems = this.modelMapper.map(users, UserWithSoldProductsDto[].class);

        String json = this.gson.toJson(usersWithSoldItems);
        System.out.println(json);
    }
    //public UserService(UserRepository userRepository) {}
    public void importData() throws IOException {
        Path path = Path.of("C:\\Users\\georg\\Spring_Data\\JSONProcessing\\src\\main\\resources\\json_files\\users.json");
        List<String> lines = Files.readAllLines(path);
        UserImportDto[] fromJson = gson.fromJson(String.join("", lines), UserImportDto[].class);
        for (UserImportDto userDto : fromJson) {
            if (userDto.getLastName() == null || userDto.getLastName().length() < 3) {
                System.err.println("User lastName is invalid!");
                continue;
            }
            User user = modelMapper.map(userDto, User.class);
            userRepository.save(user);
        }
        //Read JSON -> DTO
        //Validate
        //DTO ->ENTITY
        //Persist

    }

    public User getRandomUser() {
        long count = userRepository.count();

        if (count == 0) return null;
        Random random = new Random();
        long id = random.nextLong(1, count + 1);

        while (true) {
            Optional<User> byId = this.userRepository.findById(id);

            if (byId.isPresent()) {
                return byId.get();
            }

            id = random.nextLong(1, count + 1);
        }

    }
}
