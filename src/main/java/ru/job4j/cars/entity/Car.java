package ru.job4j.cars.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int year;

    private int mileage;

    private Brand brand;

    private Model model;

    private Engine engine;

    private BodyType bodyType;

    private List<Driver> drivers;

//    private


}
