package ru.job4j.cars.persistence;

import ru.job4j.cars.entity.Car;

import java.sql.SQLException;

public interface Storage {

    Car findCarById(int id) throws SQLException;

    Car saveCar(Car car) throws SQLException;
}
