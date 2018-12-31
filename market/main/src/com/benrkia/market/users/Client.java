package com.benrkia.market.users;

import com.benrkia.market.orders.Cart;
import com.benrkia.market.orders.Order;
import com.benrkia.market.address.Address;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Client extends Person {

    private Cart cart;
    private Collection<Order> orders;

    public Client(long id, String firstName, String lastName, Date birthdate, String email, String password, Address address, Cart cart) {
        super(id, firstName, lastName, birthdate, email, password, address);
        orders = new ArrayList<>();
        this.cart = cart;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public boolean hasCart(){
        return cart!=null;
    }
}
