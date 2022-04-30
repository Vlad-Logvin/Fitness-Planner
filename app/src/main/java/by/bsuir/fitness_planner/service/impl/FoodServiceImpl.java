package by.bsuir.fitness_planner.service.impl;

import android.content.Context;
import android.database.SQLException;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.bsuir.fitness_planner.model.Food;
import by.bsuir.fitness_planner.persistence.config.AppDatabase;
import by.bsuir.fitness_planner.service.FoodService;
import by.bsuir.fitness_planner.util.LocalDateTypeConverter;

public class FoodServiceImpl implements FoodService {

    private final static Logger logger = Logger.getLogger(FoodServiceImpl.class.getName());

    @Override
    public boolean eat(Context context, Food food) {
        try {
            AppDatabase.getAppDatabase(context).foodPersistence().add(food);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Food " + food.toString() + " was not eaten!");
            return false;
        }
        return true;
    }

    @Override
    public boolean removeEaten(Context context, Food food) {
        try {
            AppDatabase.getAppDatabase(context).foodPersistence().delete(food);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Food " + food.toString() + " was not removed!");
            return false;
        }
        return true;
    }

    @Override
    public List<Food> findByUserId(Context context, long userId) {
        List<Food> food;
        try {
            food = AppDatabase.getAppDatabase(context).foodPersistence().findByUserId(userId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Food with userId " + userId + " was not found");
            food = null;
        }
        return food;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Food> findByUserIdAndDate(Context context, long userId, LocalDate date) {
        List<Food> food;
        try {
            food = AppDatabase.getAppDatabase(context).foodPersistence()
                    .findByUserIdAndDate(userId, LocalDateTypeConverter.localDateToEpoch(date));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Food with userId " + userId + " and date " + date + " was not found");
            food = null;
        }
        return food;
    }

    @Override
    public Food findById(Context context, long id) {
        Food food;
        try {
            food = AppDatabase.getAppDatabase(context).foodPersistence().findById(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Food with id " + id + " was not found");
            food = null;
        }
        return food;
    }
}
