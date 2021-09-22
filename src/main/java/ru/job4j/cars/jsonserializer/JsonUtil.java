package ru.job4j.cars.jsonserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cars.entity.*;

public class JsonUtil {

    public static final Gson GSON_AD = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .registerTypeAdapter(City.class, new CitySerializer())
            .registerTypeAdapter(Brand.class, new BrandSerializer())
            .registerTypeAdapter(Model.class, new ModelSerializer())
            .registerTypeAdapter(BodyType.class, new BodyTypeSerializer())
            .registerTypeAdapter(Engine.class, new EngineTypeSerializer())
            .registerTypeAdapter(CarPhoto.class, new PhotoSerializer())
            .registerTypeAdapter(Transmission.class, new TransmissionSerializer())
            .registerTypeAdapter(Car.class, new CarSerializer())
            .registerTypeAdapter(User.class, new UserSerializer())
            .create();

    public static final Gson GSON_USER = new GsonBuilder().registerTypeAdapter(
            User.class, new UserSerializer()
    ).create();
}
