package by.bsuir.fitness_planner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;

import by.bsuir.fitness_planner.util.LocalDateTypeConverter;

@Entity(tableName = "rests")
public class Rest {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "user_id")
    private long userId;

    @ColumnInfo(name = "created")
    @TypeConverters(value = LocalDateTypeConverter.class)
    private LocalDate created;

    @ColumnInfo
    private int amount;
}
