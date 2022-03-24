package by.bsuir.fitness_planner.service;

import android.content.Context;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.model.Food;

public interface FoodService {
    boolean eat(Context context, Food food);

    boolean removeEaten(Context context, Food food);

    List<Food> findByUserId(Context context, long userId);

    List<Food> findByUserIdAndDate(Context context, long userId, LocalDate date);

    Food findById(Context context, long id);
}
