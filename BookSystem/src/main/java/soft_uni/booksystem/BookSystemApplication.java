package soft_uni.booksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import soft_uni.booksystem.init.DataSeeder;

@SpringBootApplication
public class BookSystemApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookSystemApplication.class, args);
    }

}
