package by.bsuir.fitness_planner.service.impl;

import android.content.Context;
import android.database.SQLException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.persistence.config.AppDatabase;
import by.bsuir.fitness_planner.service.UserService;

public class UserServiceImpl implements UserService {

    private final static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public User save(Context context, User user) {
        try {
            AppDatabase.getAppDatabase(context).userPersistence().save(user);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "User " + user.toString() + " was not saved!");
            return null;
        }
        return user;
    }

    @Override
    public User findById(Context context, long id) {
        User user;
        try {
            user = AppDatabase.getAppDatabase(context).userPersistence().findById(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "User with id " + id + " was not found");
            user = null;
        }
        return user;
    }

    @Override
    public User findByEmail(Context context, String email) {
        User user;
        try {
            user = AppDatabase.getAppDatabase(context).userPersistence().findByEmail(email);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "User with email " + email + " was not found");
            user = null;
        }
        return user;
    }

    @Override
    public List<User> findAll(Context context) {
        List<User> users;
        try {
            users = AppDatabase.getAppDatabase(context).userPersistence().findAll();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Users can't be found!");
            return null;
        }
        return users;
    }

    @Override
    public void deleteAll(Context context) {
        try {
            AppDatabase.getAppDatabase(context).userPersistence().deleteAll();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Users can't be deleted!");
        }
    }

    @Override
    public User findLast(Context context, boolean isLast) {
        User user;
        try {
            user = AppDatabase.getAppDatabase(context).userPersistence().findLast(isLast);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Last user was not found");
            user = null;
        }
        return user;
    }

    @Override
    public User delete(Context context, User user) {
        try {
            AppDatabase.getAppDatabase(context).userPersistence().delete(user);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "User " + user.toString() + " was not deleted | existed!");
            return null;
        }
        return user;
    }


}
