package ru.job4j.cars.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.entity.Advertisement;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AdRepository implements AutoCloseable {

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
            + "join fetch ad.city ci "
            + "left join fetch cr.drivers drs ";

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
                    SELECT_AD.concat("where ad.created >= :date"), Advertisement.class
            ).setParameter("date", today).list();
        });
    }

    public Collection<Advertisement> getAdsWithPhoto() throws URISyntaxException {
        List<Integer> ids = getAdsIdWithPhoto();
        List<Advertisement> ads = null;
        if (ids != null) {
            ads = tx(session -> session.createQuery(
                    SELECT_AD.concat("where ad.id in (:ids)"), Advertisement.class
            ).setParameter("ids", ids).list());
        }
        return ads;
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

    public Collection<Advertisement> getAdsWithPhotoAndByBrand(int brandId) throws URISyntaxException {
        List<Integer> adsId = getAdsIdWithPhoto();
        return adsId == null ? tx(session -> session.createQuery(
                                SELECT_AD.concat(" where brd.id = :brand_id"), Advertisement.class
                               ).setParameter("brand_id", brandId).list())
                             : tx(session -> session.createQuery(
                                SELECT_AD.concat(" where brd.id = :brand_id")
                                 .concat(" and ad.id in (:adsId)"), Advertisement.class
                         ).setParameter("brand_id", brandId).setParameter("adsId", adsId).list());
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
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private List<Integer> getAdsIdWithPhoto() throws URISyntaxException {
        String resources = File.separator + "carphoto";
        String folderName;
        List<Advertisement> ads = null;
        folderName = new File(
                Thread.currentThread().getContextClassLoader().getResource(resources).toURI()
        ).getAbsolutePath();
        String[] fileNames = new File(folderName).list();
        List<Integer> adsId = null;
        if (fileNames != null && fileNames.length > 1) {
            adsId = Arrays.stream(fileNames)
                    .filter(s -> !s.split("\\.")[0].equals("notfound"))
                    .map(s -> Integer.parseInt(s.split("\\.")[0]))
                    .collect(Collectors.toList());
        }
        return adsId;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
