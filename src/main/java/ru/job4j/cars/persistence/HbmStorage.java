package ru.job4j.cars.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.cars.entity.*;

import java.sql.SQLException;
import java.util.Collection;
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
            + "join fetch ad.city ci "
            + "left join fetch cr.drivers drs ";

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
    public Collection<Brand> getAllBrands() {
        return tx(session ->
                session.createQuery(SELECT_BRANDS, Brand.class).list()
        );
    }

    @Override
    public Collection<City> getAllCites() {
        return tx(session ->
                session.createQuery(
                        "select distinct ct from City ct ", City.class
                ).list()
        );
    }

    @Override
    public Collection<Model> getAllModels() {
        return tx(session ->
                session.createQuery(
                        "select distinct md from Model md ", Model.class
                ).list()
        );
    }

    @Override
    public Collection<Engine> getAllEngines() {
        return tx(session ->
                session.createQuery(
                        "select distinct en from Engine en ", Engine.class
                ).list()
        );
    }

    @Override
    public Collection<BodyType> getAllBodyTypes() {
        return tx(session ->
                session.createQuery(
                        "select distinct bt from BodyType bt ", BodyType.class
                ).list()
        );
    }

    @Override
    public Collection<Transmission> getAllTransmissions() {
        return tx(session ->
                session.createQuery(
                        "select distinct tr from Transmission tr ", Transmission.class
                ).list()
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
}