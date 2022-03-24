package by.bsuir.fitness_planner.service;

import android.content.Context;

import by.bsuir.fitness_planner.model.User;

public interface UserService {
    User save(Context context, User user);
    User findById(Context context, long id);
    User findByEmail(Context context, String email);
    User delete(Context context, User user);
}
