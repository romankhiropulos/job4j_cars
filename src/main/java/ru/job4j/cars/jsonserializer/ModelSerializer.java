package ru.job4j.cars.jsonserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.job4j.cars.entity.*;

import java.lang.reflect.Type;

public class ModelSerializer implements JsonSerializer<Model> {
    @Override
    public JsonElement serialize(Model model, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("id", model.getId());
        result.addProperty("name", model.getName());
        return result;
    }
}
