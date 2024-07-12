package com.revature.util.auth;

import com.revature.Account.*;
import com.revature.User.*;
import com.revature.util.auth.*;
import com.revature.util.exceptions.*;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User[] findAll() {
        // Implement findAll method if needed
        return new User[0];
    }

    public User findById(int userId) {
        // Implement findById method if needed
        return null;
    }

    public void registerUser(String username, String password) throws InvalidInputException {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.save(newUser);
    }

    public boolean loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        // TODO: Check password
        return true;
    }
}

