package ru.job4j.cars.jsonserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.job4j.cars.entity.*;

import java.lang.reflect.Type;

public class PhotoSerializer implements JsonSerializer<CarPhoto> {
    @Override
    public JsonElement serialize(CarPhoto photo,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("id", photo.getId());
        result.addProperty("name", photo.getName());
        return result;
    }
}