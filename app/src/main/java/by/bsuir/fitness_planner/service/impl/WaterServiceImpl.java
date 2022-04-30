package by.bsuir.fitness_planner.service.impl;

import android.content.Context;
import android.database.SQLException;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.bsuir.fitness_planner.model.Water;
import by.bsuir.fitness_planner.persistence.config.AppDatabase;
import by.bsuir.fitness_planner.service.WaterService;
import by.bsuir.fitness_planner.util.LocalDateTypeConverter;

public class WaterServiceImpl implements WaterService {

    private final static Logger logger = Logger.getLogger(WaterServiceImpl.class.getName());

    @Override
    public boolean drink(Context context, Water water) {
        try {
            AppDatabase.getAppDatabase(context).waterPersistence().add(water);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Water " + water.toString() + " was not drunk!");
            return false;
        }
        return true;
    }

    @Override
    public boolean removeDrunk(Context context, Water water) {
        try {
            AppDatabase.getAppDatabase(context).waterPersistence().delete(water);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Water " + water.toString() + " was not removed!");
            return false;
        }
        return true;
    }

    @Override
    public List<Water> findByUserId(Context context, long userId) {
        List<Water> water;
        try {
            water = AppDatabase.getAppDatabase(context).waterPersistence().findByUserId(userId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Water with userId " + userId + " was not found");
            water = null;
        }
        return water;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Water> findByUserIdAndDate(Context context, long userId, LocalDate date) {
        List<Water> water;
        try {
            water = AppDatabase.getAppDatabase(context).waterPersistence()
                    .findByUserIdAndDate(userId, LocalDateTypeConverter.localDateToEpoch(date));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Water with userId " + userId + " and date " + date + " was not found");
            water = null;
        }
        return water;
    }

    @Override
    public Water findById(Context context, long id) {
        Water water;
        try {
            water = AppDatabase.getAppDatabase(context).waterPersistence().findById(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Water with id " + id + " was not found");
            water = null;
        }
        return water;
    }
}
