package ru.job4j.cars.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.cars.entity.Advertisement;
import ru.job4j.cars.entity.Brand;
import ru.job4j.cars.entity.Car;
import ru.job4j.cars.entity.User;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class HbmStorage implements Storage, AutoCloseable {
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

    private static final String SELECT_USER = "select distinct us from User us "
            + "join fetch us.advertisements ";

    private static final String SELECT_CAR = "select distinct cr from Car cr "
            + "join fetch cr.model mod "
            + "join fetch cr.brand brd "
            + "join fetch cr.engine eng "
            + "join fetch cr.bodyType bt "
            + "join fetch cr.transmission trm "
            + "join fetch cr.drivers drs "
            + "where cr.id = :car_id";

    private static final String SELECT_BRANDS = "select distinct br from Brand br ";

    private static final String SELECT_BRANDS_WITH_MODELS = "select distinct br from Brand br "
            + "join fetch br.model mod";

    private static final class Lazy {
        private static final Storage INST = new HbmStorage();
    }

    public static Storage getInstance() {
        return Lazy.INST;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Override
    public Car findCarById(int id) throws SQLException {
        return tx(
                session -> {
                    final Query query = session.createQuery(SELECT_CAR);
                    query.setParameter("car_id", id);
                    return (Car) query.uniqueResult();
                }
        );
    }

    @Override
    public Car saveCar(Car car) throws SQLException {
        tx(session -> session.save(car));
        return car;
    }

    @Override
    public Advertisement saveAdvertisement(Advertisement ad) throws SQLException {
        tx(session -> session.save(ad));
        return ad;
    }

    @Override
    public void updateAdvertisement(Advertisement ad) throws SQLException {
        tu(session -> session.update(ad));
    }

    @Override
    public Collection<Advertisement> getAllAdvertisements() throws SQLException {
        return tx(session ->
                session.createQuery(SELECT_AD, Advertisement.class).list()
        );
    }

    @Override
    public Advertisement findAdById(int id) throws SQLException {
        return tx(
                session -> session.createQuery(
                        SELECT_AD.concat("where ad.id = :ad_id"), Advertisement.class
                ).setParameter("ad_id", id).uniqueResult()
        );
    }

    @Override
    public Collection<Advertisement> findAdsByBrand(int brandId) throws SQLException {
        return tx(session -> session.createQuery(
                        SELECT_AD.concat("where brd.id = :brand_id"), Advertisement.class
                ).setParameter("brand_id", brandId).list()
        );
    }

    @Override
    public Collection<Advertisement> findAdsByLastDay(String filter) {
        return tx(session -> {
            Date today = getToday();
            return session.createQuery(
                    SELECT_AD.concat("where ad.created > :date"), Advertisement.class
            ).setParameter("date", today).list();
        });
    }

    @Override
    public Collection<Advertisement> findAdsByPhoto(String filter) {
        return tx(session -> session.createQuery(
                        SELECT_AD.concat("where ph is not null"), Advertisement.class
                ).list()
        );
    }

    @Override
    public Collection<Advertisement> findAdsByLastDayAndBrand(String filter, int brandId) {
        return tx(session -> {
            Date today = getToday();
            return session.createQuery(
                    SELECT_AD.concat("where ad.created > :date ")
                             .concat("and brd.id = :brand_id"), Advertisement.class
            ).setParameter("date", today).setParameter("brand_id", brandId).list();
        });
    }

    @Override
    public Collection<Advertisement> findAdsByPhotoAndBrand(String filter, int brandId) {
        return tx(session -> session.createQuery(
                        SELECT_AD
                                .concat("where ph is not null")
                                .concat(" and brd.id = :brand_id"), Advertisement.class
                ).setParameter("brand_id", brandId).list()
        );
    }

    @Override
    public Collection<Advertisement> findAdBySold(boolean key) throws SQLException {
        return tx(
                session -> {
                    final Query query = session.createQuery(
                            "from ru.job4j.cars.entity.Advertisement where sold =: ad_sold "
                    );
                    query.setParameter("ad_sold", key);
                    return query.list();
                }
        );
    }

    @Override
    public User findUserById(int id) throws SQLException {
        return tx(
                session -> session.createQuery(
                        SELECT_USER.concat("where us.id = :user_id"), User.class
                ).setParameter("user_id", id).uniqueResult()
        );
    }

    @Override
    public User findUserByLoginAndPassword(String login, String password) throws SQLException {
        return tx(
                session -> {
                    final Query query = session.createQuery(
                            "from ru.job4j.cars.entity.User where login =: user_login"
                                    + " and password =: user_password"
                    );
                    query.setParameter("user_login", login);
                    query.setParameter("user_password", password);
                    List users = query.list();
                    return users.size() == 1 ? (User) query.list().get(0) : null;
                }
        );
    }

    @Override
    public User findUserByLogin(String login) throws SQLException {
        return tx(
                session -> {
                    final Query query = session.createQuery(
                            "from ru.job4j.cars.entity.User where login =: user_login"
                    );
                    query.setParameter("user_login", login);
                    return (User) query.uniqueResult();
                }
        );
    }

    @Override
    public User saveUser(User user) throws SQLException {
        Integer generateIdentifier = (Integer) tx(session -> session.save(user));
        return user;
    }

    @Override
    public List<Brand> getAllBrands() {
        return tx(session ->
                session.createQuery(SELECT_BRANDS, Brand.class).list()
        );
    }

    @Override
    public List<Brand> getBrandsById(int id) {
        return tx(session ->
                session.createQuery(
                        SELECT_BRANDS.concat("where br.id = :brand_id"), Brand.class
                ).setParameter("brand_id", id).list()
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

    private void tu(final Consumer<Session> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            command.accept(session);
            tx.commit();
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