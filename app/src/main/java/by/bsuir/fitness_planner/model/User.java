package by.bsuir.fitness_planner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import by.bsuir.fitness_planner.util.LocalDateTypeConverter;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private String email;

    @ColumnInfo
    private String name;

    @ColumnInfo(name = "birth_date")
    @TypeConverters(value = LocalDateTypeConverter.class)
    private LocalDate birthDate;

    @ColumnInfo
    private String gender;

    @ColumnInfo
    private double weight;

    @ColumnInfo
    private double height;

    @Relation(parentColumn = "user_id", entityColumn = "id")
    private List<Food> foods;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Double.compare(user.weight, weight) == 0 && Double.compare(user.height, height) == 0 && Objects.equals(email, user.email) && Objects.equals(name, user.name) && Objects.equals(birthDate, user.birthDate) && Objects.equals(gender, user.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, birthDate, gender, weight, height);
    }

    public User() {
    }

    public User(long id, String email, String name, LocalDate birthDate, String gender, double weight, double height) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }
}
