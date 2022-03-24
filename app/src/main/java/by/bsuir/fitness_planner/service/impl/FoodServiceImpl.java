package by.bsuir.fitness_planner.service.impl;

import android.content.Context;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.model.Food;
import by.bsuir.fitness_planner.service.FoodService;

public class FoodServiceImpl implements FoodService {
    @Override
    public boolean eat(Context context, Food food) {
        return false;
    }

    @Override
    public boolean removeEaten(Context context, Food food) {
        return false;
    }

    @Override
    public List<Food> findByUserId(Context context, long userId) {
        return null;
    }

    @Override
    public List<Food> findByUserIdAndDate(Context context, long userId, LocalDate date) {
        return null;
    }

    @Override
    public Food findById(Context context, long id) {
        return null;
    }
}
