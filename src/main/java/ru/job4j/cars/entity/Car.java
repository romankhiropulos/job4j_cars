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

    private int power;

    private int size;

    @ManyToOne
    @JoinColumn(name = "brand_id", foreignKey = @ForeignKey(name = "CAR_BRAND_ID_FKEY"))
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "model_id", foreignKey = @ForeignKey(name = "CAR_MODEL_ID_FKEY"))
    private Model model;

    @ManyToOne
    @JoinColumn(name = "engine_id", foreignKey = @ForeignKey(name = "CAR_ENGINE_ID_FKEY"))
    private Engine engine;

    @ManyToOne
    @JoinColumn(name = "body_type_id", foreignKey = @ForeignKey(name = "CAR_BODY_TYPE_ID_FKEY"))
    private BodyType bodyType;

    @ManyToOne
    @JoinColumn(name = "transmission_id", foreignKey = @ForeignKey(name = "CAR_TRANSMISSION_ID_FKEY"))
    private Transmission transmission;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private List<Driver> drivers;

    public Car() {
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }
//
//    public List<Driver> getDrivers() {
//        return drivers;
//    }
//
//    public void setDrivers(List<Driver> drivers) {
//        this.drivers = drivers;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Car car = (Car) o;

        if (id != car.id) {
            return false;
        }
        if (year != car.year) {
            return false;
        }
        if (mileage != car.mileage) {
            return false;
        }
        if (power != car.power) {
            return false;
        }
        if (size != car.size) {
            return false;
        }
        if (!brand.equals(car.brand)) {
            return false;
        }
        if (!model.equals(car.model)) {
            return false;
        }
        if (!engine.equals(car.engine)) {
            return false;
        }
        if (!bodyType.equals(car.bodyType)) {
            return false;
        }
        if (!transmission.equals(car.transmission)) {
            return false;
        }
//        return drivers.equals(car.drivers);
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + year;
        result = 31 * result + mileage;
        result = 31 * result + power;
        result = 31 * result + size;
        result = 31 * result + brand.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + engine.hashCode();
        result = 31 * result + bodyType.hashCode();
        result = 31 * result + transmission.hashCode();
//        result = 31 * result + drivers.hashCode();
        return result;
    }

}
