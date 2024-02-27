package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {

    private final static String PASSWORD = "postgres";
    private final static String USERNAME = "postgres";
    private final static String URL = "jdbc:postgresql://localhost:5432/postgres";

    static {
        loadDriver(); // Это необходимо для того, что бы работать с кодом написанным до Java 1.8
    }

    private Util(){

    }

    public static Connection open(){
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // реализуйте настройку соеденения с БД
}
