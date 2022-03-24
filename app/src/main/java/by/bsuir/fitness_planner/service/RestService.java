package by.bsuir.fitness_planner.service;

import android.content.Context;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.model.Rest;

public interface RestService {
    boolean rest(Context context, Rest rest);

    boolean removeRested(Context context, Rest rest);

    List<Rest> findByUserId(Context context, long userId);

    List<Rest> findByUserIdAndDate(Context context, long userId, LocalDate date);

    Rest findById(Context context, long id);
}
