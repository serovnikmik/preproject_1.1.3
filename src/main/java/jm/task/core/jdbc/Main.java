package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();

        service.createUsersTable();

        List<User> users = new ArrayList<>();
        users.add(new User("Rick", "Sanchez", (byte) 70));
        users.add(new User("Morty", "Smith", (byte) 14));
        users.add(new User("Summer", "Smith", (byte) 18));
        users.add(new User("Jerry", "Smith", (byte) 35));
        for (User user: users){
            service.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем %s добавлен в базу данных\n", user.getName());
        }

        service.removeUserById(1);

        List<User> allUsers = service.getAllUsers();
        System.out.println("Полученные из таблицы юзеры:");
        for (User user: allUsers){
            System.out.println(user);
        }

        service.cleanUsersTable();

        service.dropUsersTable();
    }
}
