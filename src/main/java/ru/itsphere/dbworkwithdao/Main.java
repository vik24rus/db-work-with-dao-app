package ru.itsphere.dbworkwithdao;

import ru.itsphere.dbworkwithdao.dao.AuthorDao;
import ru.itsphere.dbworkwithdao.dao.AuthorDaoJdbcImpl;
import ru.itsphere.dbworkwithdao.dao.ConnectionFactory;
import ru.itsphere.dbworkwithdao.domain.Author;

import java.util.Scanner;

public class Main {

    private static final String KEYWORD_FOR_EXIT = "--stop";
    private static AuthorDao authorDao = null;

    static {
        authorDao = new AuthorDaoJdbcImpl(ConnectionFactory.getInstance());
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in);) {
            System.out.println("Enter '--stop' to exit or id");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (isStopTheApp(line)) {
                    break;
                }

                long id;
                try {
                    id = Long.parseLong(line);
                } catch (Exception e) {
                    System.out.println("Invalid id :" + line);
                    System.out.println("Enter '--stop' to exit or id");
                    continue;
                }

                Author author = null;
                try {
                    author = authorDao.getById(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(author);
                System.out.println("Enter '--stop' to exit or id");
            }
        }
    }


    private static boolean isStopTheApp(String line) {
        return KEYWORD_FOR_EXIT.equals(line);
    }
}
