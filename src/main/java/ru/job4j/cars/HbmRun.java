package ru.job4j.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.entity.Brand;
import ru.job4j.cars.entity.Model;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {

    /**
     * В данном случае с помощью join fetch мы изменим стратегию загрузки связанных сущностей прямо в запросе к БД.
     * Это один из способов избежать проблему (LazyInitializationException), связаную с попыткой получить доступ
     * к вложенному объекту вне текущей сессии, с которой ассоциирован этот объект, и поэтому за пределами сессии
     * он уже становится недоступен.
     * @param args
     */
    public static void main(String[] args) {
        List<Brand> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            list = session.createQuery("select distinct b from Brand b join fetch b.models").list();
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Brand brand : list) {
            for (Model model : brand.getModels()) {
                System.out.println(model.getId() + " " + model.getName() + " " + brand.getName());
            }
        }
    }
}