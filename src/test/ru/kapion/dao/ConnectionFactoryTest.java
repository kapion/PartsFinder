package ru.kapion.dao;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConnectionFactoryTest {

    @Test
    public void getInstance() {
        assertNotNull(ConnectionFactory.getInstance());
    }

    @Test
    public void getConnection() {
        assertNotNull(ConnectionFactory.getInstance().getConnection());
    }
}