package org.example.spring_introduction.service;

import org.example.spring_introduction.entity.User;
import org.example.spring_introduction.repositoy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void register(User user) {
        //Convert data to User
        //Validate input
        //Check if username is taken
     userRepository.save(user);
    }
}
