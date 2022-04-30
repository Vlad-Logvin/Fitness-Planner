package by.bsuir.fitness_planner.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import by.bsuir.fitness_planner.model.Food;

@Dao
public interface FoodPersistence {
    @Insert
    void add(Food food);

    @Query(value = "SELECT * FROM foods WHERE id = :id")
    Food findById(long id);

    @Query(value = "SELECT * FROM foods WHERE user_id = :userId")
    List<Food> findByUserId(long userId);

    @Query(value = "SELECT * FROM foods WHERE user_id = :userId AND created = :date")
    List<Food> findByUserIdAndDate(long userId, Long date);

    @Delete
    void delete(Food food);
}
