package ru.job4j.cars.simpletask.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public class CandidateStorage implements AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final Logger LOG = LoggerFactory.getLogger(CandidateStorage.class.getName());

    public static CandidateStorage getInstance() {
        return CandidateStorage.Lazy.INST;
    }

    public static void main(String[] args) throws Exception {
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;
        String name1 = "RockyBalboa";
        String name2 = "IvanDrago";
        String name3 = "ApolloCreed";
        Candidate candidate1 = Candidate.of(id1, name1, 2, 250);
        Candidate candidate2 = Candidate.of(id2, name2, 3, 300);
        Candidate candidate3 = Candidate.of(id3, name3, 1, 200);
        JobStore jobstore   = JobStore.of(1, "StorageWithJobs");
        Vacancy vacancy1   = Vacancy.of(1, "Java Developer", "Writing Java CRUDs");
        Vacancy vacancy2   = Vacancy.of(2, "ABAP Developer", "Writing ABAP CRUDs");
        jobstore.addVacancy(vacancy1);
        jobstore.addVacancy(vacancy2);
        candidate2.setJobStore(jobstore);

        try {
            getInstance().saveCandidate(candidate1);
            getInstance().saveCandidate(candidate3);
            getInstance().getAllCandidates().forEach(System.out::println);
            System.out.println(getInstance().getCandidateById(id3));
            System.out.println(getInstance().getCandidateByName(name1));
            candidate1.setName("MikeTyson");
            candidate1.setExperience(4);
            candidate1.setSalary(400);
            getInstance().updateCandidate(candidate1);
            System.out.println(candidate1);
            getInstance().deleteCandidate(id1);
            getInstance().getAllCandidates().forEach(System.out::println);

            getInstance().saveCandidate(candidate2);
            Candidate candidate = getInstance().getCandidateById(id2);
            System.out.println(candidate);
            System.out.println(candidate.getJobStore());
            System.out.println(candidate.getJobStore().getVacancies());
        } catch (Exception exception) {
            LOG.error(exception.getMessage(), exception);
        } finally {
            CandidateStorage.getInstance().deleteCandidate(id1);
            CandidateStorage.getInstance().deleteCandidate(id2);
            CandidateStorage.getInstance().deleteCandidate(id3);
        }
    }

    private static final class Lazy {
        private static final CandidateStorage INST = new CandidateStorage();
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
        Candidate candidate = new Candidate();
        candidate.setId(id);
        tu(session -> session.delete(candidate));
    }

    public Collection<Candidate> getAllCandidates() throws SQLException {
        return tx(session ->
                session.createQuery("from Candidate").list()
        );
    }

    public Candidate getCandidateById(int id) throws SQLException {
        return tx(
                session -> session.createQuery(
                        "select distinct can from Candidate can "
                                + "join fetch can.jobStore js "
                                + "join fetch js.vacancies v "
                                + "where can.id = :canId", Candidate.class
                ).setParameter("canId", id).uniqueResult()
        );
    }

    public Collection<Candidate> getCandidateByName(String name) throws SQLException {
        return tx(
                session -> session.createQuery(
                        "from Candidate c where c.name = :c_name", Candidate.class
                ).setParameter("c_name", name).list()
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
