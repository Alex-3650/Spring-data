package soft_uni.jsonprocessing.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import soft_uni.jsonprocessing.dtos.ProductImportDto;
import soft_uni.jsonprocessing.dtos.UnsoldProductInfoDto;
import soft_uni.jsonprocessing.entities.Product;
import soft_uni.jsonprocessing.entities.User;
import soft_uni.jsonprocessing.repository.ProductRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, UserService userService, Gson gson, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }


    public void getUnsoldProductsInRange(double min, double max) {
        BigDecimal lower = BigDecimal.valueOf(min);
        BigDecimal upper = BigDecimal.valueOf(max);

        List<Product> products = this.productRepository.findByPriceBetweenAndBuyerIsNullOrderByPriceAsc(lower, upper);

        TypeMap<Product,UnsoldProductInfoDto > typeMap =
                this.modelMapper.createTypeMap(Product.class, UnsoldProductInfoDto .class);

//        typeMap.addMapping(source -> source.getSeller().getFirstName() + " " + source.getSeller().getLastName(),
//                          (destination, value) -> destination.setSeller(value.toString()));
//        UnsoldProductInfoDto[] result = this.modelMapper.map(products, UnsoldProductInfoDto[].class);

        List<UnsoldProductInfoDto> list = new ArrayList<>();
        for (Product product : products) {
            UnsoldProductInfoDto unsoldProduct = new UnsoldProductInfoDto(product);
            list.add(unsoldProduct);
        }
        String json = this.gson.toJson(list);
        System.out.println(json);

    }
    public void importData() throws IOException {
        Path path = Path.of("C:\\Users\\georg\\Spring_Data\\JSONProcessing\\src\\main\\resources\\json_files\\products.json");
        List<String> lines = Files.readAllLines(path);
        ProductImportDto[] productDto = gson.fromJson(String.join("", lines), ProductImportDto[].class);

        for (ProductImportDto productImportDto : productDto) {
            String name = productImportDto.getName();
            if (name == null || name.length()<3) {
                System.out.println("Invalid product name " +name);
                continue;
            }
            Product product = modelMapper.map(productImportDto, Product.class);
            product.setSeller(this.getRandomUser(false));
            product.setBuyer(this.getRandomUser(true));
            product.setCategories(this.categoryService.getRandomCategories());

            this.productRepository.save(product);

        }

    }

    private User getRandomUser(boolean canReturnNull) {
        Random rnd = new Random();

        if (canReturnNull) {
            boolean nullResult = rnd.nextBoolean();
            if (nullResult) {
                return null;
            }
        }
     return this.userService.getRandomUser();
    }
}
