package ru.job4j.cars.entity;

import javax.persistence.*;

@Entity
@Table(name = "engine")
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    private int power;

    private int size;

    public Engine(String type, int power, int size) {
        this.type = type;
        this.power = power;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Engine engine = (Engine) o;

        if (id != engine.id) {
            return false;
        }
        if (power != engine.power) {
            return false;
        }
        if (size != engine.size) {
            return false;
        }
        return type.equals(engine.type);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type.hashCode();
        result = 31 * result + power;
        result = 31 * result + size;
        return result;
    }
}
