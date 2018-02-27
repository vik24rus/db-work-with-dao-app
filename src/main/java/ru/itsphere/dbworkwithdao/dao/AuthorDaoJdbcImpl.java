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
    public static final String INSERT_AUTHOR = "INSERT INTO authors( id ,name , trade_union) values (?,?,?)";
    public static final String SELECT_ALL_AUTHORS = "SELECT * FROM authors";
    public static final String UPDATE_AUTHOR = "UPDATE authors SET name = ?, trade_union = ? WHERE id = ? " ;
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM authors WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM authors";
    public static final String FIND_ID = "SELECT id FROM authors WHERE id = ?";
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
        long id = author.getId();
        String name = author.getName();
        String trade_union = author.getTradeUnion();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_AUTHOR);) {
            statement.setLong(1, id);
            statement.setString(2, name);
            statement.setString(3, trade_union);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(String.format("Method insert(id: '%d', name: '%s' , trade_union: '%s') has thrown an exception.", id, name,trade_union), e);
        }
    }

    @Override
    public List<Author> getAll() {
        List<Author> all = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();) {
            try (ResultSet resultSet = statement.executeQuery(SELECT_ALL_AUTHORS);) {
                while (resultSet.next()) {
                    all.add(new Author(resultSet.getLong(COLUMN_ID),
                            resultSet.getString(COLUMN_NAME),
                            resultSet.getString(COLUMN_UNION)));
                }
            }
        } catch (Exception e) {
            throw new DaoException(String.format("Method getAll has thrown an exception."), e);
        }
        return null;
    }

    @Override
    public void update(Author author) {
        String name = author.getName();
        String trade_union = author.getTradeUnion();
        long id = author.getId();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_AUTHOR);) {
            statement.setString(1, name);
            statement.setString(2, trade_union);
            statement.setLong(3, id);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(String.format("Method insert(name: '%s' , trade_union: '%s' , id: '%d') has thrown an exception.", name,trade_union,id), e);
        }
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
