package by.bsuir.fitness_planner.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import by.bsuir.fitness_planner.model.Sleep;

@Dao
public interface SleepPersistence {
    @Insert
    void add(Sleep sleep);

    @Query(value = "SELECT * FROM sleeps WHERE id = :id")
    Sleep findById(long id);

    @Query(value = "SELECT * FROM sleeps WHERE user_id = :userId")
    List<Sleep> findByUserId(long userId);

    @Query(value = "SELECT * FROM sleeps WHERE user_id = :userId AND created = :date")
    List<Sleep> findByUserIdAndDate(long userId, Long date);

    @Delete
    void delete(Sleep sleep);
}
