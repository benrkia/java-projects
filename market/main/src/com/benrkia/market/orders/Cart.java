package com.benrkia.market.orders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class Cart {

    private long id;
    private Date creationDate;
    private double totalPrice;
    private Collection<LineCmd> lineCmds;

    public Cart(long id, Date creationDate, double totalPrice) {
        this.id = id;
        this.creationDate = creationDate;
        this.totalPrice = totalPrice;
        this.lineCmds = new ArrayList<>();
    }

    public Collection<LineCmd> getLineCmds() {
        return lineCmds;
    }

    public void addLineCmd(LineCmd lineCmd) {
        if(lineCmd != null) {
            lineCmds.add(lineCmd);
            this.totalPrice += lineCmd.getPrice();
        }
    }

    public void removeLineCmd(LineCmd lineCmd) {

        if(lineCmds.remove(lineCmd))
            totalPrice -= lineCmd.getPrice();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getTotalPrice() {
        return Math.abs(totalPrice);
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
