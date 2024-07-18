package com.revature.util.auth;

import com.revature.User.*;

import javax.security.sasl.AuthenticationException;

public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login(String username, String password) throws AuthenticationException {
        User user = userService.findByUsernameAndPassword(username, password);
        if (user == null) throw new AuthenticationException("Invalid user credentials. Please try again.");
        return user;
    }
}

