package com.revature.util.interfaces;

import io.javalin.Javalin;

import io.javalin.Javalin;
import io.javalin.http.Context;

public interface Controller {
    void registerPaths(Javalin app);

}
