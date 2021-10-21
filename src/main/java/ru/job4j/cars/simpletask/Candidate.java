package ru.job4j.cars.simpletask;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Candidate {

    @Id
    private int id;

    private String name;

    private int experience;

    private int salary;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private JobStore jobStore;

    public static Candidate of(int id, String name, int experience, int salary) {
        Candidate candidate = new Candidate();
        candidate.id = id;
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        return candidate;
    }

    public JobStore getJobStore() {
        return jobStore;
    }

    public void setJobStore(JobStore jobStore) {
        this.jobStore = jobStore;
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate)) {
            return false;
        }
        Candidate that = (Candidate) o;
        return this.getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Candidate{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", experience=").append(experience);
        sb.append(", salary=").append(salary);
        sb.append('}');
        return sb.toString();
    }
}
