package by.bsuir.fitness_planner.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import by.bsuir.fitness_planner.model.User;

@Dao
public interface UserPersistence {
    @Insert
    void save(User user);

    @Query(value = "SELECT * FROM users WHERE id = :id")
    User findById(long id);

    @Query(value = "SELECT * FROM users WHERE email = :email")
    User findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE isLast = :isLast")
    User findLast(boolean isLast);

    @Query(value = "SELECT * FROM users")
    List<User> findAll();

    @Query(value = "DELETE FROM users")
    void deleteAll();

    @Delete
    void delete(User user);
}
