package ru.job4j.cars.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "advertisement")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private User owner;

    private Car car;
}
