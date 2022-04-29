package by.bsuir.fitness_planner.service;

import android.content.Context;

import java.util.List;

import by.bsuir.fitness_planner.model.User;

public interface UserService {
    User save(Context context, User user);
    User findById(Context context, long id);
    User findByEmail(Context context, String email);
    List<User> findAll(Context context);
    void deleteAll(Context context);
    User findLast(Context context, boolean isLast);
    User delete(Context context, User user);
}
