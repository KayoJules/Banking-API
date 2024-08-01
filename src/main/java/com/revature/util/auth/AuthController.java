package com.revature.util.auth;

import com.revature.User.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.revature.util.interfaces.Controller;

import javax.security.sasl.AuthenticationException;
import java.lang.management.MemoryManagerMXBean;

import java.util.Scanner;

public class AuthController implements Controller{

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.post("/login", this::postLogin);
        app.get("/user-info", this::getRedirect);
    }

    private void getRedirect(Context ctx){
        ctx.redirect("https://i.pinimg.com/736x/6a/6d/11/6a6d1124cf69e5588588bc7e397598f6.jpg");
    }


    private void postLogin(Context ctx){
        String username = ctx.queryParam("username");
        String password = ctx.queryParam("password");

        try {
            User user = authService.login(username, password);
            ctx.header("userId", String.valueOf(user.getUserId()));
            ctx.status(200);
        } catch (AuthenticationException e) {
            ctx.status(HttpStatus.UNAUTHORIZED);
        }
    }

}

