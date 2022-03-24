package by.bsuir.fitness_planner.service;

import by.bsuir.fitness_planner.service.impl.FoodServiceImpl;
import by.bsuir.fitness_planner.service.impl.RestServiceImpl;
import by.bsuir.fitness_planner.service.impl.SleepServiceImpl;
import by.bsuir.fitness_planner.service.impl.UserServiceImpl;
import by.bsuir.fitness_planner.service.impl.WaterServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    private final UserService userService = new UserServiceImpl();

    public UserService getUserService() {
        return userService;
    }

    private final SleepService sleepService = new SleepServiceImpl();

    public SleepService getSleepService() {
        return sleepService;
    }

    private final WaterService waterService = new WaterServiceImpl();

    public WaterService getWaterService() {
        return waterService;
    }

    private final RestService restService = new RestServiceImpl();

    public RestService getRestService() {
        return restService;
    }

    private final FoodService foodService = new FoodServiceImpl();

    public FoodService getFoodService() {
        return foodService;
    }
}
