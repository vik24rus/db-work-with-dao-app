package ru.itsphere.dbworkwithdao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для создания подключений к базеданных
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class ConnectionFactory {

    public static final String SQlite_DRIVER = "org.sqlite.jdbc";
    public static final String DB_URL = "jdbc:sqlite:src/main/java/ru/itsphere/dbworkwithdao/workwithdao.db";


    private static ConnectionFactory instance;

    private ConnectionFactory() {
        try {
            Class.forName(SQlite_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Not found class " + SQlite_DRIVER, e);
        }
    }

    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
