Работа с базой данных через шаблон DAO

http://it-channel.ru/2016/02/25/db-work-with-dao/


Ветки две : 1) полностью готово 2) для экспериментов


В моем случае я использую SQLite


Скрипт для БД SQLite:

CREATE TABLE `authors` (

	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,

	`name`	TEXT NOT NULL,

	`trade_union`	TEXT

);


Должно получиться:

1 -------------------------

Author{id=51, name='Sasha', tradeUnion='World union'}

Author{id=52, name='Pasha', tradeUnion='World union'}

Author{id=53, name='Dasha', tradeUnion='null'}

2 -------------------------

Author{id=51, name='Sasha', tradeUnion='World union'}

Author{id=52, name='Pasha', tradeUnion='World union'}

Author{id=53, name='Dasha', tradeUnion='European union'}

3 -------------------------

Author{id=53, name='Dasha', tradeUnion='European union'}

4 -------------------------

Author{id=51, name='Sasha', tradeUnion='World union'}

Author{id=52, name='Pasha', tradeUnion='World union'}

5 -------------------------


PS:

Ваша задача до реализовать этот интерфейс в этом классе.

Собственно класс AuthorDaoJdbcImpl это единственное место где Вы должны писать код в этом задании.



Обратите внимание на то, что Вы должны получать Connection используя класс ConnectionFactory.

И используя его делать свои манипуляции. Не забывайте закрывать Connection-ы, Statement-ы и ResultSet-ы.

Обрабатывайте исключения анналогично тому как я сделал это в методе getById и не забывайте Писать осмысленные сообщения об ошибках.

Описание того, что должны делать методы, которые Вы реализуете смотри интерфейс AuthorDao, там все описано.
