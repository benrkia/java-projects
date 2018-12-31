package com.benrkia.bank.dao;

import com.benrkia.bank.data.Account;
import com.benrkia.bank.data.Card;

import java.util.*;

public class AccountDao implements IDao<Account> {

    Map<Long, Account> accounts = new HashMap<>();

    private static AccountDao accountDao = new AccountDao();

    private AccountDao(){
        UserDao userDao = UserDao.getInstance();
        accounts.put(1L, new Account(1, "39489839829839389", 20000, userDao.get(1), "94567836478912", "398", "08", "2020"));
        accounts.put(2L, new Account(2, "98379379279379793", 100000, userDao.get(2), "94893849837684", "644", "05", "2019"));
        accounts.put(3L, new Account(3, "37834783784783748", 4000, userDao.get(5), "98394893849398", "984", "12", "2020"));
        accounts.put(4L, new Account(4, "19894803882739862", 100, userDao.get(4), "43984938090590", "274", "01", "2021"));
        accounts.put(5L, new Account(5, "79693276376283683", 500, userDao.get(3), "09430948758587", "059", "03", "2019"));
        accounts.put(6L, new Account(6, "38928938938938987", 75000, userDao.get(2), "98398498409509", "263", "09", "2030"));
        accounts.put(7L, new Account(7, "93892893289283928", 5000, userDao.get(7), "89438498398493", "498", "04", "2019"));
        accounts.put(8L, new Account(8, "89379279379279279", 30, userDao.get(6), "84938498398498", "290", "11", "2019"));
        accounts.put(9L, new Account(9, "23327982782798237", 400, userDao.get(7), "48948985948958", "349", "02", "2020"));
        accounts.put(10L, new Account(10, "83784783748738748", 3545, userDao.get(1), "84985984958984", "238", "04", "2024"));

    }

    public static AccountDao getInstance(){
        return accountDao;
    }

    @Override
    public Account get(long id) {
        return this.accounts.get(id);
    }

    @Override
    public Collection<Account> getAll() {
        return this.accounts.values();
    }

    public Collection<Card> getCards(){
        Collection<Card> cards = new ArrayList<>();
        for(Account account:getAll()){
            cards.add(account.getCard());
        }

        return cards;
    }
}
