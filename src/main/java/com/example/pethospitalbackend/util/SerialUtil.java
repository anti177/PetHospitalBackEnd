package com.example.pethospitalbackend.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SerialUtil {

    private static final Gson gson = new Gson();

    public static String toJsonStr(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJsonStr(String jsonStr, Class<T> clazz) {
        return gson.fromJson(jsonStr, clazz);
    }

    public static <T> T fromJsonStr(String jsonStr, TypeToken<T> typeOfT) {
        return gson.fromJson(jsonStr, typeOfT.getType());
    }

}
