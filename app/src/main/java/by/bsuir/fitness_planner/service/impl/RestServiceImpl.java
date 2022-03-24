package by.bsuir.fitness_planner.service.impl;

import android.content.Context;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.model.Rest;
import by.bsuir.fitness_planner.service.RestService;

public class RestServiceImpl implements RestService {
    @Override
    public boolean rest(Context context, Rest rest) {
        return false;
    }

    @Override
    public boolean removeRested(Context context, Rest rest) {
        return false;
    }

    @Override
    public List<Rest> findByUserId(Context context, long userId) {
        return null;
    }

    @Override
    public List<Rest> findByUserIdAndDate(Context context, long userId, LocalDate date) {
        return null;
    }

    @Override
    public Rest findById(Context context, long id) {
        return null;
    }
}
