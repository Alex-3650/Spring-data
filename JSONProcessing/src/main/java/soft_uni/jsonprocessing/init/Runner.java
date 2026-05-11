package soft_uni.jsonprocessing.init;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import soft_uni.jsonprocessing.services.CategoryService;
import soft_uni.jsonprocessing.services.ProductService;
import soft_uni.jsonprocessing.services.UserService;

import java.io.Serializable;

@Component
public class Runner implements CommandLineRunner{
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public Runner(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }


    @Override
    public void run(String... args) throws Exception {
//    productService.getUnsoldProductsInRange(300,7000);
//      this.userService.getAllWithSoldProducts();
        //this.categoryService.getCategoryStats();
        this.userService.getAllWithAtLeastOneSoldProduct();

    }
}
