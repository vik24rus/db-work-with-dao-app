package ru.itsphere.dbworkwithdao.dao;

import ru.itsphere.dbworkwithdao.domain.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 *
 * Реализация AuthorDao через JDBC.
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class AuthorDaoJdbcImpl implements AuthorDao {

    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM authors WHERE id = ?";
    public static final String INSERT_AUTHOR = "    ";
    public static final String SELECT_ALL_AUTHOR = "    ";
    public static final String UPDATE_AUTHOR = "    ";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM authors WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM authors";
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
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY);) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    return new Author(resultSet.getLong(COLUMN_ID),
                            resultSet.getString(COLUMN_NAME),
                            resultSet.getString(COLUMN_UNION));
                }
            }
        } catch (Exception e) {
            throw new DaoException(String.format("Method getById(id: '%d') has thrown an exception.", id), e);
        }
        return null;
    }

    @Override
    public void insert(Author author) {

    }

    @Override
    public List<Author> getAll() {


        return null;
    }

    @Override
    public void update(Author author) {

    }

    @Override
    public void deleteById(long id) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY);) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(String.format("Method deleteById(id: '%d') has thrown an exception.", id), e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ALL);) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(String.format("Method deleteAll has thrown an exception."), e);
        }
    }
}
