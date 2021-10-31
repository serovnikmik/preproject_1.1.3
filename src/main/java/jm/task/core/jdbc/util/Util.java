package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public Util(){
        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e){
            System.out.println("Не удалось подключиться к базе данных");;
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
