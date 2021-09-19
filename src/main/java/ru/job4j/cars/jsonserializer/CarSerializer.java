package ru.job4j.cars.jsonserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.job4j.cars.entity.*;

import java.lang.reflect.Type;

public class CarSerializer implements JsonSerializer<Car> {
    @Override
    public JsonElement serialize(Car car,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {

        JsonObject result = new JsonObject();
        result.addProperty("id", car.getId());
        result.addProperty("year", car.getYear());
        result.addProperty("power", car.getPower());
        result.addProperty("size", car.getSize());
        result.addProperty("mileage", car.getMileage());
        result.add("brand", jsonSerializationContext.serialize(car.getBrand()));
        result.add("model", jsonSerializationContext.serialize(car.getModel()));
        result.add("bodyType", jsonSerializationContext.serialize(car.getBodyType()));
        result.add("engine", jsonSerializationContext.serialize(car.getEngine()));
        result.add(
                "transmission",
                        jsonSerializationContext.serialize(car.getTransmission())
        );
        return result;
    }
}