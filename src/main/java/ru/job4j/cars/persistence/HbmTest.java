package ru.job4j.cars.persistence;

import ru.job4j.cars.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class HbmTest {
    public static void main(String[] args) {
//        Car car = new Car();
//        car.setBodyType(new BodyType("Седан"));
//        car.setBrand(new Brand("GAZ"));
//        car.setDrivers(new ArrayList<>(Arrays.asList(new Driver("Chelik"))));
////        car.setEngine(new Engine("Gazoline", 333, 4));
//        car.setMileage(20);
//        car.setModel(new Model("Pobeda", new Brand("Sedan")));
//        car.setTransmission(new Transmission("auto"));
//        car.setYear(2018);
        Car car = null;
        try {
            car = HbmStorage.getInstance().findCarById(1);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println(Objects.requireNonNull(car).toString());
    }
}