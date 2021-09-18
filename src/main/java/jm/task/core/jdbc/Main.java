package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String DRIVER = "com.mysql.сj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        //создание объекта для проведения операций с базой данных
        UserDao userDao = new UserDaoJDBCImpl();

//        создание таблицы
        userDao.createUsersTable();

//        создание и добавление 4-х User-ов(+ вывод в консоль)
        List<User> users = new ArrayList<>();
        users.add(new User("Rick", "Sanchez", (byte) 70));
        users.add(new User("Morty", "Smith", (byte) 14));
        users.add(new User("Summer", "Smith", (byte) 18));
        users.add(new User("Jerry", "Smith", (byte) 35));
        for (User user: users){
            userDao.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем %s добавлен в базу данных\n", user.getName());
        }

//        удаление User-а c id = 1
        userDao.removeUserById(1);

//        получение всех User-ов(+ вывод в консоль)
        List<User> allUsers = userDao.getAllUsers();
        System.out.println("Полученные из таблицы юзеры:");
        for (User user: allUsers){
            System.out.println(user);
        }

//        очистка таблицы юзеров
        userDao.cleanUsersTable();

//        удаление таблицы юзеров
        userDao.dropUsersTable();
    }
}
