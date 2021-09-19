package ru.job4j.cars;

import ru.job4j.cars.entity.*;
import ru.job4j.cars.persistence.HbmStorage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class HbmTest {
    public static void main(String[] args) {
        Car car = null;
        Advertisement ad = null;
        User user = null;
        try {
            user = HbmStorage.getInstance().findUserById(1);
            car = HbmStorage.getInstance().findCarById(1);
            ad = HbmStorage.getInstance().findAdById(1);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println(car);
        System.out.println(ad);
        System.out.println(user);
    }
}