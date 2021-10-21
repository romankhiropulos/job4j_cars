package ru.job4j.cars.simpletask;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.SQLException;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public class CandidateStorage implements AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final CandidateStorage INST = new CandidateStorage();
    }

    public static CandidateStorage getInstance() {
        return CandidateStorage.Lazy.INST;
    }

    private static final int ID_1 = 1;
    private static final int ID_2 = 2;
    private static final int ID_3 = 3;

    private static final String NAME_1 = "RockyBalboa";
    private static final String NAME_2 = "IvanDrago";
    private static final String NAME_3 = "ApolloCreed";

    public static final Candidate CANDIDATE_1;
    private static final Candidate CANDIDATE_2;
    private static final Candidate CANDIDATE_3;

    static {
        CANDIDATE_1 = Candidate.of(ID_1, NAME_1, 2, 250);
        CANDIDATE_2 = Candidate.of(ID_2, NAME_2, 3, 300);
        CANDIDATE_3 = Candidate.of(ID_3, NAME_3, 1, 200);
    }

    public static void main(String[] args) throws Exception {
        try {
            getInstance().saveCandidate(CANDIDATE_1);
            getInstance().saveCandidate(CANDIDATE_2);
            getInstance().saveCandidate(CANDIDATE_3);
            getInstance().getAllCandidates().forEach(System.out::println);
            System.out.println(getInstance().getCandidateById(ID_2));
            System.out.println(getInstance().getCandidateByName(NAME_1));
            CANDIDATE_1.setName("MikeTyson");
            CANDIDATE_1.setExperience(4);
            CANDIDATE_1.setSalary(400);
            getInstance().updateCandidate(CANDIDATE_1);
            System.out.println(CANDIDATE_1);
            getInstance().deleteCandidate(ID_1);
            getInstance().getAllCandidates().forEach(System.out::println);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            CandidateStorage.getInstance().deleteCandidate(ID_1);
            CandidateStorage.getInstance().deleteCandidate(ID_2);
            CandidateStorage.getInstance().deleteCandidate(ID_3);
        }
    }

    public Candidate saveCandidate(Candidate candidate) throws SQLException {
        tx(session -> session.save(candidate));
        return candidate;
    }

    public void updateCandidate(Candidate candidate) throws SQLException {
        tu(session -> session.createQuery(
                        "update Candidate c set c.name = :newName, c.experience = :newExp,"
                                + " c.salary = :newSalary where c.id = :c_id"
                )
                .setParameter("newName", candidate.getName())
                .setParameter("newExp", candidate.getExperience())
                .setParameter("newSalary", candidate.getSalary())
                .setParameter("c_id", candidate.getId())
                .executeUpdate());
    }

    public void deleteCandidate(int id) throws SQLException {
        tu(session -> session.createQuery(
                "delete from Candidate c where c.id = :c_id"
        ).setParameter("c_id", id).executeUpdate());
    }

    public Collection<Candidate> getAllCandidates() throws SQLException {
        return tx(session ->
                session.createQuery("from Candidate").list()
        );
    }

    public Candidate getCandidateById(int id) throws SQLException {
        return tx(
                session -> session.createQuery(
                        "from Candidate c where c.id = :c_id", Candidate.class
                ).setParameter("c_id", id).uniqueResult()
        );
    }

    public Candidate getCandidateByName(String name) throws SQLException {
        return tx(
                session -> session.createQuery(
                        "from Candidate c where c.name = :c_name", Candidate.class
                ).setParameter("c_name", name).uniqueResult()
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

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
