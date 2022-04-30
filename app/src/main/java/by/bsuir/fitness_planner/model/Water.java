package by.bsuir.fitness_planner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;

import by.bsuir.fitness_planner.util.LocalDateTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity(tableName = "waters",
        foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id", onDelete = ForeignKey.SET_NULL)})
@AllArgsConstructor
public class Water {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "user_id")
    private long userId;

    @ColumnInfo(name = "created")
    @TypeConverters(value = LocalDateTypeConverter.class)
    private LocalDate created;

    @ColumnInfo
    private double amount;
}
