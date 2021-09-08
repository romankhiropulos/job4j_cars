package ru.job4j.cars.persistence;

import ru.job4j.cars.entity.Advertisement;
import ru.job4j.cars.entity.Car;

import java.sql.SQLException;
import java.util.Collection;

public interface Storage {

    Car findCarById(int id) throws SQLException;

    Advertisement findAdById(int id) throws SQLException;

    Car saveCar(Car car) throws SQLException;

    Advertisement saveAdvertisement(Advertisement ad) throws SQLException;

    void updateAdvertisement(Advertisement ad) throws SQLException;

    Collection<Advertisement> getAllAdvertisements() throws SQLException;
}
