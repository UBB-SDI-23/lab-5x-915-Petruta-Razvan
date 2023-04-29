package com.example.restapi.service.user_service;

import com.example.restapi.exceptions.UserNotFoundException;
import com.example.restapi.model.user.User;
import com.example.restapi.repository.user_repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}
