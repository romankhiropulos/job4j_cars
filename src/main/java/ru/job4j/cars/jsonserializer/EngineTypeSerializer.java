package ru.job4j.cars.jsonserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.job4j.cars.entity.*;

import java.lang.reflect.Type;

public class EngineTypeSerializer implements JsonSerializer<Engine> {
    @Override
    public JsonElement serialize(Engine engine,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("id", engine.getId());
        result.addProperty("name", engine.getType());
        return result;
    }
}