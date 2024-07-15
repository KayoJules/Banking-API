package com.revature;

import com.revature.User.*;
import com.revature.util.auth.*;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import org.slf4j.LoggerFactory;

// TODO: JavaDocs

public class BankFrontController {

//    public static final Logger logger = LoggerFactory.getLogger(BankFrontController.class);


    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson());
        });

        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);
        AuthController authController = new AuthController(authService);


        app.start(8080);
    }
}
