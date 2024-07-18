package com.revature.User;

import com.revature.util.exceptions.DataNotFoundException;
import com.revature.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

import java.util.Objects;

public class UserController implements Controller {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.get("/users", this::getAllUsers);
        app.post("/users", this::postNewUser);
        app.get("/users/{userId}", this::getUserById);
        app.put("/users", this::putUpdateUser);
        app.delete("/users", this::deleteUser);

    }

    private void getAllUsers(Context ctx) {
        List<User> users = userService.findAll();
        ctx.json(users);
    }

    private void postNewUser(Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);
        User user = userService.create(newUser);

        ctx.status(201);
        ctx.json(user);
    }

    private void getUserById(Context ctx) {
        int userId = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("userId")));

        try {
            User foundUser = userService.findById(userId);
            ctx.json(foundUser);

        } catch (DataNotFoundException e) {
            ctx.status(404);
            ctx.result(e.getMessage());
        }
    }

    private void putUpdateUser(Context ctx) {
        int userId = loggedInCheck(ctx);
        if (userId == -1) { return; }

        User updatedUser = ctx.bodyAsClass(User.class);

        if (updatedUser.getUserId() == userId) {
            userService.update(updatedUser);
            ctx.status(202);
            ctx.result("Member has been updated.");
        } else {
            ctx.status(403);
            ctx.result("You are not logged in as the member you're trying to update. Please try again");
        }

    }

    private void deleteUser(Context ctx) {
        int userId = loggedInCheck(ctx);
        if (userId == -1) return;

        User toDelete = new User();
        toDelete.setUserId(userId);

        if (!userService.delete(toDelete)) {
            ctx.status(400);
        } else {
            ctx.status(204);
        }
    }

    private int loggedInCheck(Context ctx) {
        String headerUserId = ctx.header("userId");

        if (headerUserId == null) {
            ctx.status(400);
            ctx.result("You are logged in.");
            return -1;
        }
        return Integer.parseInt(headerUserId);
    }

}
