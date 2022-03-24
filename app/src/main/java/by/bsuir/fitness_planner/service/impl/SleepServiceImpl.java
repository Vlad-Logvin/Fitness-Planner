package by.bsuir.fitness_planner.service.impl;

import android.content.Context;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.model.Sleep;
import by.bsuir.fitness_planner.service.SleepService;

public class SleepServiceImpl implements SleepService {
    @Override
    public boolean sleep(Context context, Sleep sleep) {
        return false;
    }

    @Override
    public boolean removeSlept(Context context, Sleep sleep) {
        return false;
    }

    @Override
    public List<Sleep> findByUserId(Context context, long userId) {
        return null;
    }

    @Override
    public List<Sleep> findByUserIdAndDate(Context context, long userId, LocalDate date) {
        return null;
    }

    @Override
    public Sleep findById(Context context, long id) {
        return null;
    }
}
