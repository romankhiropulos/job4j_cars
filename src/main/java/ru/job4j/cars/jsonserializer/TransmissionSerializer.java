package ru.job4j.cars.jsonserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.job4j.cars.entity.*;

import java.lang.reflect.Type;

public class TransmissionSerializer implements JsonSerializer<Transmission> {
    @Override
    public JsonElement serialize(Transmission transmission,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("id", transmission.getId());
        result.addProperty("name", transmission.getName());
        return result;
    }
}
