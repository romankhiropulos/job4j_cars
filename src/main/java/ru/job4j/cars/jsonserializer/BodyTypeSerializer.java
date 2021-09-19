package ru.job4j.cars.jsonserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.job4j.cars.entity.*;

import java.lang.reflect.Type;

public class BodyTypeSerializer implements JsonSerializer<BodyType> {
    @Override
    public JsonElement serialize(BodyType bodyType, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("id", bodyType.getId());
        result.addProperty("name", bodyType.getName());
        return result;
    }
}
