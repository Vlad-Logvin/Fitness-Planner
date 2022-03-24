package by.bsuir.fitness_planner.service;

import android.content.Context;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.model.Water;

public interface WaterService {
    boolean drink(Context context, Water water);

    boolean removeDrunk(Context context, Water water);

    List<Water> findByUserId(Context context, long userId);

    List<Water> findByUserIdAndDate(Context context, long userId, LocalDate date);

    Water findById(Context context, long id);
}
