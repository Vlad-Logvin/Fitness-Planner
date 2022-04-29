package by.bsuir.fitness_planner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;

@Data
@Entity(tableName = "users", indices = {@Index(value = "email", unique = true)})
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
