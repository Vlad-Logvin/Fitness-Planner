package by.bsuir.fitness_planner.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class User {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String email;

    @DatabaseField
    private String password;

    @DatabaseField
    private String name;

    @DatabaseField
    private byte age;

    @DatabaseField
    private String gender;

    @DatabaseField
    private double weight;

    @DatabaseField
    private double height;
}
