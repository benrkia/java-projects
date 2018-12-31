package com.benrkia.bank.data;

import java.util.Date;

public class Account {

    private long id;
    private String RIB;
    private Date creationDate;
    private double solde;
    private User user;
    private Card card;

    public Account(long id, String RIB, double solde, User user, String number, String cvv, String expirationMonth, String expirationYear) {
        this.id = id;
        this.RIB = RIB;
        this.creationDate = new Date();
        this.solde = solde;
        setUser(user);
        this.card = new Card(id, number, "visa", cvv, this, expirationMonth, expirationYear);
    }

    private String generateNumber(int taille){
        String number="";
        for(int i=0;i<taille;++i){
            int n = (int)(Math.random()*9);
            number += n;
        }
        return number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRIB() {
        return RIB;
    }

    public void setRIB(String RIB) {
        this.RIB = RIB;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if(this.user != null)
            this.user.removeAccount(this);
        this.user = user;
        this.user.addAccount(this);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
