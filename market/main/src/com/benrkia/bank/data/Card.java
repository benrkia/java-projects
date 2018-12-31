package com.benrkia.bank.data;

import java.util.Calendar;
import java.util.Date;

public class Card {

    private long id;
    private String number;
    private String type;
    private String cvv;
    private Date creationDate;
    private String expirationMonth;
    private String expirationYear;
    private Account account;

    public Card(long id, String number, String type, String cvv, Account account, String expirationMonth, String expirationYear) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.cvv = cvv;
        this.account = account;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.creationDate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }
}
