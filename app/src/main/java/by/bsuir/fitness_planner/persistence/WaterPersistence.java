package by.bsuir.fitness_planner.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.model.Water;

@Dao
public interface WaterPersistence {
    @Insert
    void add(Water water);

    @Query(value = "SELECT * FROM waters WHERE id = :id")
    Water findById(long id);

    @Query(value = "SELECT * FROM waters WHERE user_id = :userId")
    List<Water> findByUserId(long userId);

    @Query(value = "SELECT * FROM waters WHERE user_id = :userId AND created = :date")
    List<Water> findByUserIdAndDate(long userId, Long date);

    @Delete
    void delete(Water water);
}
