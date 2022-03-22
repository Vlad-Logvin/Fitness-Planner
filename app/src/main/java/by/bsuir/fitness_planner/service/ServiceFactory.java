package by.bsuir.fitness_planner.service;

import android.content.Context;

import by.bsuir.fitness_planner.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    private final UserService userService = new UserServiceImpl();

    public UserService getUserService() {
        return userService;
    }
}
