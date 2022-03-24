package by.bsuir.fitness_planner.service.impl;

import android.content.Context;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.model.Water;
import by.bsuir.fitness_planner.service.WaterService;

public class WaterServiceImpl implements WaterService {
    @Override
    public boolean drink(Context context, Water water) {
        return false;
    }

    @Override
    public boolean removeDrunk(Context context, Water water) {
        return false;
    }

    @Override
    public List<Water> findByUserId(Context context, long userId) {
        return null;
    }

    @Override
    public List<Water> findByUserIdAndDate(Context context, long userId, LocalDate date) {
        return null;
    }

    @Override
    public Water findById(Context context, long id) {
        return null;
    }
}
