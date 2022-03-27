package by.bsuir.fitness_planner.util;

import com.google.gson.Gson;

public class JSONUtil {
    private static final JSONUtil instance = new JSONUtil();

    public static JSONUtil getInstance() {
        return instance;
    }

    private final Gson gson = new Gson();

    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public Object fromJson(String json, Class<?> cl) {
        return gson.fromJson(json, cl);
    }
}
