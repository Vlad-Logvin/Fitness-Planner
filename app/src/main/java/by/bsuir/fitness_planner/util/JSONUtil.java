package by.bsuir.fitness_planner.util;

import com.google.gson.Gson;

public class JSONUtil {
    private static final JSONUtil instance = new JSONUtil();
    private final Gson gson = new Gson();

    public static JSONUtil getInstance() {
        return instance;
    }

    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public Object fromJson(String json, Class<?> cl) {
        return gson.fromJson(json, cl);
    }
}
