package ru.job4j.cars.simpletask.hql;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vacancy {

    @Id
    private int id;

    private String name;

    private String description;

    public static Vacancy of(int id, String name, String description) {
        Vacancy a = new Vacancy();
        a.name = name;
        a.description = description;
        a.id = id;
        return a;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vacancy{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
