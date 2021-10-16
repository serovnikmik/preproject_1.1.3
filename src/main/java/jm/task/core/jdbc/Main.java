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

    public static void main(String[] args) {
//        создание объекта для проведения операций с базой данных
        UserServiceImpl service = new UserServiceImpl();

//        создание таблицы
        service.createUsersTable();

//        создание и добавление 4-х User-ов(+ вывод в консоль)
        List<User> users = new ArrayList<>();
        users.add(new User("Rick", "Sanchez", (byte) 70));
        users.add(new User("Morty", "Smith", (byte) 14));
        users.add(new User("Summer", "Smith", (byte) 18));
        users.add(new User("Jerry", "Smith", (byte) 35));
        for (User user: users){
            service.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем %s добавлен в базу данных\n", user.getName());
        }

//        удаление User-а c id = 1
        service.removeUserById(1);

//        получение всех User-ов(+ вывод в консоль)
        List<User> allUsers = service.getAllUsers();
        System.out.println("Полученные из таблицы юзеры:");
        for (User user: allUsers){
            System.out.println(user);
        }

//        очистка таблицы юзеров
        service.cleanUsersTable();

//        удаление таблицы юзеров
        service.dropUsersTable();
    }
}
