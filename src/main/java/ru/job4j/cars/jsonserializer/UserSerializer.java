package ru.job4j.cars.jsonserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.job4j.cars.entity.*;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {

        JsonObject result = new JsonObject();
        result.addProperty("id", user.getId());
        result.addProperty("login", user.getLogin());
        return result;
    }
}
