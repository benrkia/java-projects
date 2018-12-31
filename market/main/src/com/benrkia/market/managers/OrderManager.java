package com.benrkia.market.managers;

import com.benrkia.market.address.Address;
import com.benrkia.market.configuration.Answer;
import com.benrkia.market.dao.OrderDAO;
import com.benrkia.market.orders.Cart;
import com.benrkia.market.orders.Order;
import com.benrkia.market.orders.OrderType;
import com.benrkia.market.users.Client;
import com.benrkia.market.users.Commercial;

public class OrderManager {

    private OrderDAO orderDAO = OrderDAO.getInstance();
    private PersonManager personManager = new PersonManager();

    public Answer addOrder(short orderType, Address shippingAddress, double totalPrice, Commercial commercial, Client client){

        if(totalPrice <= 0)
            return Answer.getInstance(false, "total price must be positive");

        Order order = new Order(-1, null, orderType, shippingAddress, totalPrice, commercial, client);
        orderDAO.add(order);

        return Answer.getInstance(true, "order has been added successfully", order);

    }

    public Answer addOrder(Cart cart, Address shippingAddress, Client client){

        Commercial commercial = personManager.getAvailableCommercial();

        return addOrder(OrderType.New, shippingAddress, cart.getTotalPrice(), commercial, client);

    }

    public Answer updateOrderType(Order order, short orderType){


        order.setOrderType(orderType);
        orderDAO.update(order);

        return Answer.getInstance(true, "order has been updated successfully", order);
    }

    public Answer updateOrderShippingAddress(Order order, Address shippingAddress){


        order.setShippingAddress(shippingAddress);
        orderDAO.update(order);

        return Answer.getInstance(true, "order has been updated successfully", order);
    }

}
