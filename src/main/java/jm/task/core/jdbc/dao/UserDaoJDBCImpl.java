package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {
    private static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users(" +
            "id INT NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(20) NULL , " +
            "lastname VARCHAR(20) NULL , " +
            "age TINYINT(1) NULL," +
            "PRIMARY KEY(id)" +
            ");";
    private static String DELETE_TABLE = "DROP TABLE IF EXISTS users;";
    private static String INSERT_NEW_USER = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?);";
    private static String GET_ALL_USERS = "SELECT * FROM users;";
    private static String ClEAR_TABLE = "TRUNCATE TABLE users;";
    private static String DELETE_USER = "DELETE FROM users WHERE id = ?;";
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable(){
        try (Connection connection = new Util().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE);
            preparedStatement.execute();
        } catch (SQLException e){
            System.out.println("Что-то не так с созданием таблицы" +
                    "/n(возможно неправильный код для создания таблицы или она уже есть)");
        }
    }

    public void dropUsersTable() {
        try(Connection connection = new Util().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TABLE);
            preparedStatement.execute();
        } catch (SQLException e){
            System.out.println("Что-то не так с удалением таблицы" +
                    "/n(возможно неправильный код для удаления таблицы или её уже нет)");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = new Util().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Что-то не так с добавлением User-а" +
                    "/n(возможно неправильный код для добавления User-а)");
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = new Util().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Что-то не так с удалением User-а" +
                    "/n(возможно неправильный код для добавления User-а)");
        }
    }

    public List<User> getAllUsers() {
        List<User> gottenUsers = new ArrayList<>();
        try(Connection connection = new Util().getConnection()){
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
        }
        return gottenUsers;
    }

    public void cleanUsersTable() {
        try(Connection connection = new Util().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(ClEAR_TABLE);
            preparedStatement.execute();
        } catch (SQLException e){
            System.out.println("Что-то не так с очисткой таблицы");
        }
    }
}
