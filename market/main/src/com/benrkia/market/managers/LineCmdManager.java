package com.benrkia.market.managers;

import com.benrkia.market.configuration.Answer;
import com.benrkia.market.dao.LineCmdDAO;
import com.benrkia.market.orders.Cart;
import com.benrkia.market.orders.LineCmd;
import com.benrkia.market.orders.LineCmdType;
import com.benrkia.market.orders.Order;
import com.benrkia.market.products.Product;
import com.benrkia.market.users.Client;
import com.benrkia.market.users.Person;

public class LineCmdManager {

    private LineCmdDAO lineCmdDAO = LineCmdDAO.getInstance();

    public Answer addLineCmd(int quantity, double price, short lineCmdType, Product product, Order order, Cart cart){

        if(quantity <= 0)
            return Answer.getInstance(false, "quantity must be positive");
        if(price <= 0)
            return Answer.getInstance(false, "price must be positive");

        LineCmd lineCmd = new LineCmd(-1, quantity, price, lineCmdType, product, order, cart);
        lineCmdDAO.add(lineCmd);

        return Answer.getInstance(true, "line item has been added successfully", lineCmd);

    }

    public Answer addLineCmd(int quantity, Product product, Cart cart){

        if(quantity > product.getQuantity())
            return Answer.getInstance(false, "this quantity doesn't exist in our stock");

        double price = product.getSellingPrice() * quantity;

        return addLineCmd(quantity, price, LineCmdType.Cart, product, null, cart);

    }

    public Answer removeLineCmd(LineCmd lineCmd){
        Cart cart = lineCmd.getCart();
        cart.removeLineCmd(lineCmd);
        lineCmdDAO.delete(lineCmd);

        return Answer.getInstance(true, "line item has been deleted successfully");
    }

    public Answer updateLineCmdQuantity(LineCmd lineCmd, int quantity){

        if(quantity <= 0)
            return Answer.getInstance(false, "quantity must be positive");

        lineCmd.setQuantity(quantity);
        lineCmdDAO.update(lineCmd);

        return Answer.getInstance(true, "line item has been updated successfully", lineCmd);
    }

    public Answer updateLineCmdPrice(LineCmd lineCmd, double price){


        if(price <= 0)
            return Answer.getInstance(false, "price must be positive");

        lineCmd.setPrice(price);
        lineCmdDAO.update(lineCmd);

        return Answer.getInstance(true, "line item has been updated successfully", lineCmd);
    }

    public Answer updateLineCmdType(LineCmd lineCmd, short lineCmdType){


        lineCmd.setLineCmdType(lineCmdType);
        lineCmdDAO.update(lineCmd);

        return Answer.getInstance(true, "line item has been updated successfully", lineCmd);
    }

    public Answer updateLineCmdOrder(LineCmd lineCmd, Order order){


        lineCmd.setOrder(order);
        lineCmdDAO.update(lineCmd);

        return Answer.getInstance(true, "line item has been updated successfully", lineCmd);
    }

    public void update(Order order, Cart cart){
        lineCmdDAO.update(order, cart);
    }

}
