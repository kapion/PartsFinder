package ru.kapion.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Aleksandr Kuznetsov (kapion) on 25.06.2018
 * Класс-фабрика для загрузки JDBC драйвера и получения соединения
 */
public final class ConnectionFactory {
    private Logger LOG = LogManager.getRootLogger();

    private final String DRIVER;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    private static ConnectionFactory factory;

    /**
     * Инициализация и загрузка JDBC драйвера, настройки берутся из файла database.ini
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private ConnectionFactory() throws ClassNotFoundException, IOException {
        Properties props = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        try(InputStream inputStream =
                    new FileInputStream(new File(classLoader.getResource("database.ini").getFile()))
        ) {
            props.load(inputStream);
            DRIVER  = props.getProperty("DRIVER");
            DB_URL  = props.getProperty("URL");
            DB_USER = props.getProperty("USER");
            DB_PASSWORD= props.getProperty("PASSWORD");
        }

        Class.forName(DRIVER);
    }

    /**
     * Singleton
     * @return
     */
    public static ConnectionFactory getInstance() {
        if (factory == null) {
            try {
                return new ConnectionFactory();
            }catch (ClassNotFoundException e) {
                System.out.println(" JdbcHelper getInstance - ClassNotFoundException "+e.getMessage());
            }catch (IOException e) {
                System.out.println("JdbcHelper getInstance - IOException "+e.getMessage());
            }

        }
        return factory;
    }

    /**
     * Получение соединения используя настройки, полученные ранее из файла
     * @return
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return connection;
    }
}
