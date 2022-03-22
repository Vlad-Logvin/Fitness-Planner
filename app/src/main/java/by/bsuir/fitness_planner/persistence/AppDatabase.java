package by.bsuir.fitness_planner.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import by.bsuir.fitness_planner.model.Food;
import by.bsuir.fitness_planner.model.Rest;
import by.bsuir.fitness_planner.model.Sleep;
import by.bsuir.fitness_planner.model.User;
import by.bsuir.fitness_planner.model.Water;

@Database(entities = {User.class, Food.class, Rest.class, Water.class, Sleep.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UserPersistence userPersistence();

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "FitnessPlanner.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

}
