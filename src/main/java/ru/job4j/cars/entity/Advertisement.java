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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // ??
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    private int price;

    private String description;

    private boolean sold;

//    private Photo photo;

    public Advertisement() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Advertisement that = (Advertisement) o;

        if (id != that.id) {
            return false;
        }
        if (sold != that.sold) {
            return false;
        }
        if (!created.equals(that.created)) {
            return false;
        }
        if (!owner.equals(that.owner)) {
            return false;
        }
        return car.equals(that.car);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + created.hashCode();
        result = 31 * result + owner.hashCode();
        result = 31 * result + car.hashCode();
        result = 31 * result + (sold ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Advertisement{");
        sb.append("id=").append(id);
        sb.append(", created=").append(created);
        sb.append(", owner=").append(owner);
        sb.append(", car=").append(car);
        sb.append(", sold=").append(sold);
        sb.append('}');
        return sb.toString();
    }
}
