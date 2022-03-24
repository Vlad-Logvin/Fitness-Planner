package by.bsuir.fitness_planner.service;

import android.content.Context;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.model.Sleep;

public interface SleepService {
    boolean sleep(Context context, Sleep sleep);

    boolean removeSlept(Context context, Sleep sleep);

    List<Sleep> findByUserId(Context context, long userId);

    List<Sleep> findByUserIdAndDate(Context context, long userId, LocalDate date);

    Sleep findById(Context context, long id);
}
