package ru.job4j.cars.entity;

import javax.persistence.*;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Brand brand;

    private Model model;

    private int year;

    private Engine engine;

    private BodyType bodyType;

//    private
}
