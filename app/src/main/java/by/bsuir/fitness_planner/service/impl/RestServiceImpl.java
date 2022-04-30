package by.bsuir.fitness_planner.service.impl;

import android.content.Context;
import android.database.SQLException;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.bsuir.fitness_planner.model.Rest;
import by.bsuir.fitness_planner.persistence.config.AppDatabase;
import by.bsuir.fitness_planner.service.RestService;
import by.bsuir.fitness_planner.util.LocalDateTypeConverter;

public class RestServiceImpl implements RestService {

    private final static Logger logger = Logger.getLogger(RestServiceImpl.class.getName());

    @Override
    public boolean rest(Context context, Rest rest) {
        try {
            AppDatabase.getAppDatabase(context).restPersistence().add(rest);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Rest " + rest.toString() + " was not rest!");
            return false;
        }
        return true;
    }

    @Override
    public boolean removeRested(Context context, Rest rest) {
        try {
            AppDatabase.getAppDatabase(context).restPersistence().delete(rest);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Rest " + rest.toString() + " was not removed!");
            return false;
        }
        return true;
    }

    @Override
    public List<Rest> findByUserId(Context context, long userId) {
        List<Rest> rest;
        try {
            rest = AppDatabase.getAppDatabase(context).restPersistence().findByUserId(userId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Rest with userId " + userId + " was not found");
            rest = null;
        }
        return rest;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Rest> findByUserIdAndDate(Context context, long userId, LocalDate date) {
        List<Rest> rest;
        try {
            rest = AppDatabase.getAppDatabase(context).restPersistence()
                    .findByUserIdAndDate(userId, LocalDateTypeConverter.localDateToEpoch(date));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Rest with userId " + userId + " and date " + date + " was not found");
            rest = null;
        }
        return rest;
    }

    @Override
    public Rest findById(Context context, long id) {
        Rest rest;
        try {
            rest = AppDatabase.getAppDatabase(context).restPersistence().findById(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Rest with id " + id + " was not found");
            rest = null;
        }
        return rest;
    }
}
