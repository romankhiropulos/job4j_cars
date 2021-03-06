package ru.job4j.cars.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "j_user")
public class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "j_user_id_seq")
    @GenericGenerator(name = "j_user_id_seq", strategy = "increment")
    @Column(name = "id")
    private int id;

    private String login;

    private String password;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Advertisement> advertisements = new ArrayList<>();

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public User(String login, String password, String name, List<Advertisement> advertisements) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.advertisements = advertisements;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAdvertisement(Advertisement advertisement) {
        advertisements.add(advertisement);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(User o) {
        return login.compareTo(o.login);
    }
}
