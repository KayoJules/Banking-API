package com.revature.util.interfaces;

import io.javalin.Javalin;

public abstract class Controller {

    void registerPaths(Javalin app) {

    }

    public abstract void registerPaths(Javalin app);
}
