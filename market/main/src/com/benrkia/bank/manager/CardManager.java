package com.benrkia.bank.manager;

import com.benrkia.bank.dao.CardDao;
import com.benrkia.bank.data.Card;

import java.util.Collection;

public class CardManager {

    private CardDao cardDao = CardDao.getInstance();

    public Card get(long id) {
        return cardDao.get(id);
    }

    public Collection<Card> getAll() {
        return cardDao.getAll();
    }

    public Card getCard(String number, String cvv, String expirationMonth, String expirationYear){
        return this.cardDao.getCard(number, cvv, expirationMonth, expirationYear);
    }
}
