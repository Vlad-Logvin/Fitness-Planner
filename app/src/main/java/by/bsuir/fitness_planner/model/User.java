package by.bsuir.fitness_planner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.time.LocalDate;

import by.bsuir.fitness_planner.util.LocalDateTypeConverter;
import lombok.Data;

@Data
@Entity(tableName = "users")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private String email;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private int age;

    @ColumnInfo
    private String gender;

    @ColumnInfo
    private double weight;

    @ColumnInfo
    private double height;

    @ColumnInfo
    private boolean isLast;
}
