package by.bsuir.fitness_planner.service;

import by.bsuir.fitness_planner.service.impl.FoodServiceImpl;
import by.bsuir.fitness_planner.service.impl.RestServiceImpl;
import by.bsuir.fitness_planner.service.impl.SleepServiceImpl;
import by.bsuir.fitness_planner.service.impl.UserServiceImpl;
import by.bsuir.fitness_planner.service.impl.WaterServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final UserService userService = new UserServiceImpl();
    private final SleepService sleepService = new SleepServiceImpl();
    private final WaterService waterService = new WaterServiceImpl();
    private final RestService restService = new RestServiceImpl();
    private final FoodService foodService = new FoodServiceImpl();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public SleepService getSleepService() {
        return sleepService;
    }

    public WaterService getWaterService() {
        return waterService;
    }

    public RestService getRestService() {
        return restService;
    }

    public FoodService getFoodService() {
        return foodService;
    }
}
