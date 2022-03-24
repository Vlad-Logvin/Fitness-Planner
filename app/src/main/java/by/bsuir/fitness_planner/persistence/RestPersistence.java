package by.bsuir.fitness_planner.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.time.LocalDate;
import java.util.List;

import by.bsuir.fitness_planner.model.Rest;

@Dao
public interface RestPersistence {
    @Insert
    void add(Rest rest);

    @Query(value = "SELECT * FROM rests WHERE id = :id")
    Rest findById(long id);

    @Query(value = "SELECT * FROM rests WHERE user_id = :userId")
    List<Rest> findByUserId(long userId);

    @Query(value = "SELECT * FROM rests WHERE user_id = :userId AND created = :date")
    List<Rest> findByUserIdAndDate(long userId, LocalDate date);

    @Delete
    void delete(Rest rest);
}
