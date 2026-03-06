package org.example.spring_introduction;

import org.example.spring_introduction.entity.User;
import org.example.spring_introduction.repositoy.UserRepository;
import org.example.spring_introduction.service.AccountServiceImpl;
import org.example.spring_introduction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Main implements CommandLineRunner {
    private final UserService userService;
    private final AccountServiceImpl accountService;

    @Autowired
    public Main(UserService userService, AccountServiceImpl accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World");
        User pesho = new User("Ivan", 23);
        userService.register(pesho);


       // userService.save(pesho);
    }
}
