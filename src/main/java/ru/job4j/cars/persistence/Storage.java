package ru.job4j.cars.persistence;

import ru.job4j.cars.entity.*;

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

    User findUserById(int id) throws SQLException;

    User findUserByLoginAndPassword(String login, String password) throws SQLException;

    User findUserByLogin(String login) throws SQLException;

    User saveUser(User user) throws SQLException;

    List<Brand> getBrandsById(int id);

    Collection<Brand> getAllBrands();

    Collection<City> getAllCites();

    Collection<Model> getAllModels();

    Collection<Engine> getAllEngines();

    Collection<BodyType> getAllBodyTypes();

    Collection<Transmission> getAllTransmissions();
}
