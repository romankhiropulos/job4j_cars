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

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @ManyToOne
    @JoinColumn(name = "body_type_id")
    private BodyType bodyType;

    @ManyToOne
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Driver> drivers;


}
