package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users(" +
            "id INT NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(20) NULL , " +
            "lastname VARCHAR(20) NULL , " +
            "age TINYINT(1) NULL," +
            "PRIMARY KEY(id)" +
            ");";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS users;";
    private static final String INSERT_NEW_USER = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?);";
    private static final String GET_ALL_USERS = "SELECT * FROM users;";
    private static final String ClEAR_TABLE = "TRUNCATE TABLE users;";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?;";

    Connection connection;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable(){
        connection = new Util().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException rollback){
                rollback.printStackTrace();
            }
            System.out.println("Что-то не так с созданием таблицы" +
                    "/n(возможно неправильный код для создания таблицы или она уже есть)");
        } finally {
            try {
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        connection = new Util().getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TABLE);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e){
            try{
                connection.rollback();
            } catch (SQLException rollback){
                rollback.printStackTrace();
            }
            System.out.println("Что-то не так с удалением таблицы" +
                    "/n(возможно неправильный код для удаления таблицы или её уже нет)");
        } finally {
            try {
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        connection = new Util().getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e){
            try{
                connection.rollback();
            } catch (SQLException rollback){
                rollback.printStackTrace();
            }
            System.out.println("Что-то не так с добавлением User-а" +
                    "/n(возможно неправильный код для добавления User-а)");
        } finally {
            try {
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        connection = new Util().getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e){
            try{
                connection.rollback();
            } catch (SQLException rollback){
                rollback.printStackTrace();
            }
            System.out.println("Что-то не так с удалением User-а" +
                    "/n(возможно неправильный код для добавления User-а)");
        } finally {
            try {
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        connection = new Util().getConnection();
        List<User> gottenUsers = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                User gottenUser = new User();
                gottenUser.setId((long)resultSet.getInt("id"));
                gottenUser.setName(resultSet.getString("name"));
                gottenUser.setLastName(resultSet.getString("lastName"));
                gottenUser.setAge(resultSet.getByte("age"));
                gottenUsers.add(gottenUser);
            }
        } catch (SQLException e){
            System.out.println("Что-то не так с получением User-ов из таблицы");
        } finally {
            try {
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return gottenUsers;
    }

    public void cleanUsersTable() {
        connection = new Util().getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(ClEAR_TABLE);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e){
            try{
                connection.rollback();
            } catch (SQLException rollback){
                rollback.printStackTrace();
            }
            System.out.println("Что-то не так с очисткой таблицы");
        } finally {
            try {
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
