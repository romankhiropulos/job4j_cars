package ru.job4j.cars.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.entity.Advertisement;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

public class AdRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final String SELECT_AD = "select distinct ad from Advertisement ad "
            + "join fetch ad.owner ow "
            + "join fetch ad.car cr "
            + "join fetch cr.model mod "
            + "join fetch cr.brand brd "
            + "join fetch brd.models mds "
            + "join fetch cr.engine eng "
            + "join fetch cr.bodyType bt "
            + "join fetch cr.transmission trm "
            + "left join fetch ad.photo ph "
            + "join fetch ad.city ci "
            + "join fetch cr.drivers drs ";

    private static final class Lazy {
        private static final AdRepository INST = new AdRepository();
    }

    public static AdRepository getInstance() {
        return AdRepository.Lazy.INST;
    }

    public Collection<Advertisement> getAdsByBrand(int brandId) throws SQLException {
        return tx(session -> session.createQuery(
                        SELECT_AD.concat("where brd.id = :brand_id"), Advertisement.class
                ).setParameter("brand_id", brandId).list()
        );
    }

    public Collection<Advertisement> getAdsByLastDay() {
        return tx(session -> {
            Date today = getToday();
            return session.createQuery(
                    SELECT_AD.concat("where ad.created > :date"), Advertisement.class
            ).setParameter("date", today).list();
        });
    }

    public Collection<Advertisement> getAdsByPhoto() {
        return tx(session -> session.createQuery(
                        SELECT_AD.concat("where ph is not null"), Advertisement.class
                ).list()
        );
    }

    public Collection<Advertisement> getAdsByLastDayAndBrand(int brandId) {
        return tx(session -> {
            Date today = getToday();
            return session.createQuery(
                    SELECT_AD.concat("where ad.created > :date ")
                            .concat("and brd.id = :brand_id"), Advertisement.class
            ).setParameter("date", today).setParameter("brand_id", brandId).list();
        });
    }

    public Collection<Advertisement> getAdsByPhotoAndBrand(int brandId) {
        return tx(session -> session.createQuery(
                       SELECT_AD.concat("where ph is not null")
                                .concat(" and brd.id = :brand_id"), Advertisement.class
                ).setParameter("brand_id", brandId).list()
        );
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private Date getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
