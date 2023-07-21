package app.resketchware.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonHelper {
    private static Gson gson;

    private GsonHelper() {}

    public static Gson get() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static String toJson(Object obj) {
        try {
            return obj == null ? null : get().toJson(obj);
        } catch (Exception e) {
            Log.d("GsonHelper", "Cannot to convert to JSON", e);
            return null;
        }
    }

    public static <T> T fromJson(String str, Class<T> clazz) {
        return fromJson(str, (Type) clazz);
    }

    public static <T> T fromJson(String str, Type type) {
        try {
            return str == null ? null : (T) get().fromJson(str, type);
        } catch (Exception e) {
            Log.d("GsonHelper", "Cannot parse JSON to object", e);
            return null;
        }
    }
}