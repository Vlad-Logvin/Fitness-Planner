package by.bsuir.fitness_planner.util;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDate;

public class LocalDateTypeConverter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate fromLong(@Nullable Long epoch) {
        return epoch == null ? null : LocalDate.ofEpochDay(epoch);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Long localDateToEpoch(@Nullable LocalDate localDate) {
        return localDate == null ? null : localDate.toEpochDay();
    }
}