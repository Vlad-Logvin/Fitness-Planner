package by.bsuir.fitness_planner.service.impl;

import android.content.Context;
import android.database.SQLException;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.bsuir.fitness_planner.model.Sleep;
import by.bsuir.fitness_planner.persistence.config.AppDatabase;
import by.bsuir.fitness_planner.service.SleepService;
import by.bsuir.fitness_planner.util.LocalDateTypeConverter;

public class SleepServiceImpl implements SleepService {

    private final static Logger logger = Logger.getLogger(SleepServiceImpl.class.getName());

    @Override
    public boolean sleep(Context context, Sleep sleep) {
        try {
            AppDatabase.getAppDatabase(context).sleepPersistence().add(sleep);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sleep " + sleep.toString() + " was not slept!");
            return false;
        }
        return true;
    }

    @Override
    public boolean removeSlept(Context context, Sleep sleep) {
        try {
            AppDatabase.getAppDatabase(context).sleepPersistence().delete(sleep);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sleep " + sleep.toString() + " was not removed!");
            return false;
        }
        return true;
    }

    @Override
    public List<Sleep> findByUserId(Context context, long userId) {
        List<Sleep> sleep;
        try {
            sleep = AppDatabase.getAppDatabase(context).sleepPersistence().findByUserId(userId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sleep with userId " + userId + " was not found");
            sleep = null;
        }
        return sleep;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Sleep> findByUserIdAndDate(Context context, long userId, LocalDate date) {
        List<Sleep> sleep;
        try {
            sleep = AppDatabase.getAppDatabase(context).sleepPersistence()
                    .findByUserIdAndDate(userId, LocalDateTypeConverter.localDateToEpoch(date));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sleep with userId " + userId + " and date " + date + " was not found");
            sleep = null;
        }
        return sleep;
    }

    @Override
    public Sleep findById(Context context, long id) {
        Sleep sleep;
        try {
            sleep = AppDatabase.getAppDatabase(context).sleepPersistence().findById(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sleep with id " + id + " was not found");
            sleep = null;
        }
        return sleep;
    }
}
