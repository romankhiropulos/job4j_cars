package ru.job4j.cars.simpletask.bookauthor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class AuthBookRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book one = Book.of("Kazanskaya");
            Book two = Book.of("Piterskaya");

            Author first =  Author.of("Nikolay");
            first.getBooks().add(one);
            first.getBooks().add(two);

            Author second = Author.of("Anatoliy");
            second.getBooks().add(two);

            session.persist(first);
            session.persist(second);

            first = session.get(Author.class, 1);
            session.remove(first);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
