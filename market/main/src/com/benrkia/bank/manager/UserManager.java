package com.benrkia.bank.manager;

import com.benrkia.bank.dao.UserDao;
import com.benrkia.bank.data.User;

import java.util.Collection;

public class UserManager {

    private UserDao userDao = UserDao.getInstance();


    public User get(long id) {
        return this.userDao.get(id);
    }

    public Collection<User> getAll() {
        return this.userDao.getAll();
    }

}
