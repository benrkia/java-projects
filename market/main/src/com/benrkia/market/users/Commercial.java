package com.benrkia.market.users;

import com.benrkia.market.orders.Order;
import com.benrkia.market.address.Address;
import com.benrkia.market.products.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Commercial extends Person {

    private Collection<Product> products;
    private Collection<Order> orders;

    public Commercial(long id, String firstName, String lastName, Date birthdate, String email, String password, Address address) {
        super(id, firstName, lastName, birthdate, email, password, address);
        products = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addOrder(Order order){
        orders.add(order);
    }
}
