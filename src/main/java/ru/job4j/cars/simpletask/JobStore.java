package ru.job4j.cars.simpletask;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class JobStore {

    @Id
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vacancy> vacancies = new HashSet<>();

    public void addVacancy(Vacancy vacancy) {
        this.vacancies.add(vacancy);
    }

    public static JobStore of(int id, String name) {
        JobStore a = new JobStore();
        a.id = id;
        a.name = name;
        return a;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JobStore{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", vacancies=").append(vacancies);
        sb.append('}');
        return sb.toString();
    }
}
