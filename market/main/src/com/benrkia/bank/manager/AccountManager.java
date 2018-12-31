package com.benrkia.bank.manager;

import com.benrkia.bank.dao.AccountDao;
import com.benrkia.bank.data.Account;
import com.benrkia.bank.data.Card;

import java.util.ArrayList;
import java.util.Collection;

public class AccountManager {

    private AccountDao accountDao = AccountDao.getInstance();

    public Account get(long id) {
        return this.accountDao.get(id);
    }

    public Collection<Account> getAll() {
        return this.accountDao.getAll();
    }

    public Collection<Card> getCards(){
        return accountDao.getCards();
    }

    public boolean debit(Card card, double cost) {

        Account account = card.getAccount();
        if(account.getSolde() >= cost){
            account.setSolde(account.getSolde() - cost);
            return true;
        }

        return false;

    }
}
