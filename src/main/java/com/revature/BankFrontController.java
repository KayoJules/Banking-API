package com.revature;

import com.revature.Account.AccountController;
import com.revature.Account.AccountRepository;
import com.revature.Account.AccountService;
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

        User userLoggedIn = null;    // Storing the user's session
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        userController.registerPaths(app);

        AccountRepository accountRepository = new AccountRepository();
        AccountService accountService = new AccountService(accountRepository);
        AccountController accountController = new AccountController(accountService);
        accountController.registerPaths(app);

        AuthService authService = new AuthService(userService);
        AuthController authController = new AuthController(authService);
        authController.registerPaths(app);


        app.start(8080);
    }
}
