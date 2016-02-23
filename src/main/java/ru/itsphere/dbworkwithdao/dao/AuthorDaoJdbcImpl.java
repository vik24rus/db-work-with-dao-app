package ru.itsphere.dbworkwithdao.dao;

import ru.itsphere.dbworkwithdao.domain.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Реализация AuthorDao через JDBC.
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class AuthorDaoJdbcImpl implements AuthorDao {

    public static final String SELECT_BY_ID = "SELECT * FROM authors WHERE id = ?";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_UNION = "trade_union";
    private ConnectionFactory connectionFactory;

    public AuthorDaoJdbcImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Author getById(long id) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    return new Author(resultSet.getLong(COLUMN_ID), resultSet.getString(COLUMN_NAME), resultSet.getString(COLUMN_UNION));
                }
            }
        } catch (Exception e) {
            throw new DaoException("getById has thrown an exception", e);
        }
        return null;
    }
}
