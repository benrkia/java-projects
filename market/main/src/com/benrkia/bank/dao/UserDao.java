package com.benrkia.bank.dao;

import com.benrkia.bank.data.Account;
import com.benrkia.bank.data.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserDao implements IDao<User> {

    Map<Long, User> users = new HashMap<>();

    private static UserDao userDao = new UserDao();

    private UserDao(){

        users.put(1L, new User(1, "ilyasse", "benrkia", "benrkiailyasse@gmail.com"));
        users.put(2L, new User(2, "achraf", "el adssaoui", "achraf@gmail.com"));
        users.put(3L, new User(3, "hatim", "joubair", "hatimjoubair@gmail.com"));
        users.put(4L, new User(4, "mohammed", "benrkia", "mohammedbenrkia@gmail.com"));
        users.put(5L, new User(5, "user", "user", "user@user.com"));
        users.put(6L, new User(6, "root", "root", "root@root.com"));
        users.put(7L, new User(7, "admin", "admin", "admin@admin.com"));

    }

    public static UserDao getInstance(){
        return userDao;
    }

    @Override
    public User get(long id) {
        return this.users.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return this.users.values();
    }

}
