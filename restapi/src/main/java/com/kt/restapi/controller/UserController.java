package com.kt.restapi.controller;

import com.kt.restapi.domain.User;
import com.kt.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> findUsers(){
        List<User> allusers = userRepository.findAll();
        return allusers;
    }

}
