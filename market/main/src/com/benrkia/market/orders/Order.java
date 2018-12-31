package com.benrkia.market.orders;

import com.benrkia.market.address.Address;
import com.benrkia.market.users.Client;
import com.benrkia.market.users.Commercial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Order {

    private long id;
    private Date creationDate;
    private short orderType;
    private Address shippingAddress;
    private double totalPrice;
    private Commercial commercial;
    private Client client;
    private Collection<LineCmd> lineCmds;

    public Order(long id, Date creationDate, short orderType, Address shippingAddress, double totalPrice, Commercial commercial, Client client) {
        this.id = id;
        this.creationDate = creationDate;
        this.orderType = orderType;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        setCommercial(commercial);
        setClient(client);
        this.lineCmds = new ArrayList<>();
    }

    public Collection<LineCmd> getLineCmds() {
        return lineCmds;
    }

    public void setLineCmds(Collection<LineCmd> lineCmds) {
        this.lineCmds = lineCmds;
        for (LineCmd lineCmd:this.lineCmds)
            lineCmd.setLineCmdType(LineCmdType.Order);
    }

    public void addLineCmd(LineCmd lineCmd) {
        lineCmds.add(lineCmd);
    }

    public Commercial getCommercial() {
        return commercial;
    }

    public void setCommercial(Commercial commercial) {
        if(this.commercial == null){
            this.commercial = commercial;
            if(this.commercial != null)
                this.commercial.addOrder(this);
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        if(this.client == null){
            this.client = client;
            if(this.client != null)
                this.client.addOrder(this);
        }
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

    public short getOrderType() {
        return orderType;
    }

    public void setOrderType(short orderType) {
        this.orderType = orderType;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
