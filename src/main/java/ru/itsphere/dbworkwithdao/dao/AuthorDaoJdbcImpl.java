package ru.itsphere.dbworkwithdao.dao;

import ru.itsphere.dbworkwithdao.domain.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
    public static final String SELECT_ALL_QUERY = "SELECT * FROM authors";
    public static final String INSERT_AUTHOR_QUERY = "INSERT INTO authors (name, trade_union) VALUES (?, ?)";
    public static final String UPDATE_AUTHOR_QUERY = "UPDATE authors SET name = ?, trade_union = ? WHERE id = ?";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM authors WHERE id = ?";
    public static final String DELETE_ALL_QUERY = "DELETE FROM authors";
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
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_AUTHOR_QUERY);) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getTradeUnion());
            int i = statement.executeUpdate();
            if (i == 0) {
                throw new DaoException("Table 'authors' was not updated", null);
            }
        } catch (Exception e) {
            throw new DaoException(String.format("Method insert(author:'%s') has thrown an exception", author), e);
        }
    }

    @Override
    public List<Author> getAll() {
        List<Author> all = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();) {
            try (ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);) {
                while (resultSet.next()) {
                    all.add(new Author(resultSet.getLong(COLUMN_ID),
                            resultSet.getString(COLUMN_NAME),
                            resultSet.getString(COLUMN_UNION)));
                }
            }
        } catch (Exception e) {
            throw new DaoException("Method getAll() has thrown an exception.", e);
        }
        return all;
    }

    @Override
    public void update(Author author) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_AUTHOR_QUERY);) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getTradeUnion());
            statement.setLong(3, author.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(String.format("Method update(author:'%s') has thrown an exception", author), e);
        }
    }

    @Override
    public void deleteById(long id) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY);) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(String.format("Method deleteById(id:'%d') has thrown an exception", id), e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_ALL_QUERY);
        } catch (Exception e) {
            throw new DaoException("Method deleteAll() has thrown an exception", e);
        }
    }
}
