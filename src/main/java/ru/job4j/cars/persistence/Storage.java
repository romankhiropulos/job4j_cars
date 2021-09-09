package ru.job4j.cars.persistence;

import ru.job4j.cars.entity.Advertisement;
import ru.job4j.cars.entity.Brand;
import ru.job4j.cars.entity.Car;
import ru.job4j.cars.entity.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface Storage {

    Car findCarById(int id) throws SQLException;

    Advertisement findAdById(int id) throws SQLException;

    Car saveCar(Car car) throws SQLException;

    Advertisement saveAdvertisement(Advertisement ad) throws SQLException;

    void updateAdvertisement(Advertisement ad) throws SQLException;

    Collection<Advertisement> getAllAdvertisements() throws SQLException;

    Collection<Advertisement> findAdBySold(boolean key) throws SQLException;

    abstract User findUserByLoginAndPassword(String login, String password) throws SQLException;

    User findUserByLogin(String login) throws SQLException;

    User saveUser(User user) throws SQLException;

    Collection<Brand> getAllBrands();
}
