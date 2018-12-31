package com.benrkia.bank.dao;

import com.benrkia.bank.data.Account;
import com.benrkia.bank.data.Card;

import java.util.Collection;

public class CardDao implements IDao<Card> {

    private static CardDao cardDao = new CardDao();

    private CardDao(){

    }

    public static CardDao getInstance(){
        return cardDao;
    }

    @Override
    public Card get(long id) {
        AccountDao accountDao = AccountDao.getInstance();
        Account account = accountDao.get(id);
        if(account == null)
            return null;
        return account.getCard();
    }

    @Override
    public Collection<Card> getAll() {
        AccountDao accountDao = AccountDao.getInstance();
        return accountDao.getCards();
    }

    public Card getCard(String number, String cvv, String expirationMonth, String expirationYear) {
        Collection<Card> cards = getAll();
        for(Card card:cards){
            if(card.getNumber().equals(number) &&
                    card.getCvv().equals(cvv) &&
                    card.getExpirationMonth().equals(expirationMonth) &&
                    card.getExpirationYear().equals(expirationYear)){
                return card;
            }
        }

        return null;
    }
}
