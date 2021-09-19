package ru.job4j.cars.jsonserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.job4j.cars.entity.*;

import java.lang.reflect.Type;

public class BrandSerializer implements JsonSerializer<Brand> {
    @Override
    public JsonElement serialize(Brand brand, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("id", brand.getId());
        result.addProperty("name", brand.getName());
        return result;
    }
}
