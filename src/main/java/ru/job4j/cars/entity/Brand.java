package ru.job4j.cars.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "brand")
    private List<Model> models = new ArrayList<>();

    public static Brand of(String name) {
        Brand brand = new Brand();
        brand.name = name;
        return brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public void addModel(Model model) {
        models.add(model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Brand brand = (Brand) o;

        if (id != brand.id) {
            return false;
        }
        if (!name.equals(brand.name)) {
            return false;
        }
        return models.equals(brand.models);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + models.hashCode();
        return result;
    }
}
