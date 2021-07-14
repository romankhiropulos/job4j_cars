package ru.job4j.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.entity.Brand;
import ru.job4j.cars.entity.Model;

import java.util.List;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Model one = Model.of("E-Class");
            Model two = Model.of("S-Class");
            Model three = Model.of("C-Class");
            Model four = Model.of("G-Class");
            Model five = Model.of("A-Class");

            Brand mercedes = Brand.of("Mercedes");
            mercedes.getModels().addAll(List.of(one, two, three, four, five));

            session.save(mercedes);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}