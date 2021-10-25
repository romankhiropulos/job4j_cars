package ru.job4j.cars.simpletask.integrationtesting;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {

    private final BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void clear() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        pool.getConnection().prepareStatement("DROP TABLE IF EXISTS orders").executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenGetTwoOrdersWithSameName() {
        OrdersStore store = new OrdersStore(pool);
        Order orderOne = new Order(1, "Jenny", "Good cat", new Timestamp(new Date().getTime()));
        Order orderTwo = new Order(2, "Jenny", "Very good cat", new Timestamp(new Date().getTime()));
        Order orderThree = new Order(3, "Dog", "Good boy", new Timestamp(new Date().getTime()));
        store.save(orderOne);
        store.save(orderTwo);
        store.save(orderThree);
        List<Order> all = (List<Order>) store.findByName("Jenny");
        assertThat(all.size(), is(2));
        assertThat(all.get(0).getName(), is(all.get(1).getName()));
    }

    @Test
    public void whenUpdateOrders() {
        OrdersStore store = new OrdersStore(pool);
        Order oldOrder = new Order(1, "Jenny", "Good cat", new Timestamp(new Date().getTime()));
        Order newOrder = new Order(1, "Jenn", "Very good cat", new Timestamp(new Date().getTime()));
        store.save(oldOrder);
        store.updateOrder(newOrder);
        assertThat(store.findById(1).getName(), is("Jenn"));
        assertThat(store.findById(1).getDescription(), is("Very good cat"));
    }

    @Test
    public void whenFindAll() {
        OrdersStore store = new OrdersStore(pool);
        Order orderOne = new Order(1, "Jenny", "Good cat", new Timestamp(new Date().getTime()));
        Order orderTwo = new Order(2, "Jenny", "Very good cat", new Timestamp(new Date().getTime()));
        Order orderThree = new Order(3, "Dog", "Good boy", new Timestamp(new Date().getTime()));
        store.save(orderOne);
        store.save(orderTwo);
        store.save(orderThree);
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(3));
    }

    @Test
    public void whenFindById() {
        OrdersStore store = new OrdersStore(pool);
        Order orderOne = new Order(1, "Jenny", "Good cat", new Timestamp(new Date().getTime()));
        Order orderTwo = new Order(2, "Jenny", "Very good cat", new Timestamp(new Date().getTime()));
        Order orderThree = new Order(3, "Dog", "Good boy", new Timestamp(new Date().getTime()));
        store.save(orderOne);
        store.save(orderTwo);
        store.save(orderThree);
        Order order =  store.findById(2);
        assertThat(order.getId(), is(2));
        assertThat(order.getDescription(), is("Very good cat"));
    }
}