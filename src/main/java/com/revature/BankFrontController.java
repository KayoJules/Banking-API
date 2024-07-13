package com.revature;

import com.revature.User.*;
import com.revature.util.auth.*;
import io.javalin.Javalin;


public class BankFrontController
{
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);
        AuthController authController = new AuthController(authService);

        Javalin app = Javalin.create();







    }
}
