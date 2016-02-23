package ru.itsphere.dbworkwithdao.dao;

import ru.itsphere.dbworkwithdao.domain.Author;

/**
 * Содержит методы необходимые для работы с объектами класса Author.
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public interface AuthorDao {

    /**
     * Возвращает объект класса Author с данными извлеченными из хранилища.
     * Для поиска используется идентификатор.
     *
     * @param id - соответствует колонке идентификаторов и БД.
     * @return объект класса Author с данными.
     */
    Author getById(long id);
}
