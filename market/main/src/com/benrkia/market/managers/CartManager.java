package com.benrkia.market.managers;

import com.benrkia.bank.Response;
import com.benrkia.market.address.Address;
import com.benrkia.market.configuration.Answer;
import com.benrkia.market.configuration.Configuration;
import com.benrkia.market.dao.CartDAO;
import com.benrkia.market.dao.LineCmdDAO;
import com.benrkia.market.orders.Cart;
import com.benrkia.market.orders.LineCmd;
import com.benrkia.market.orders.LineCmdType;
import com.benrkia.market.orders.Order;
import com.benrkia.market.products.Product;
import com.benrkia.market.users.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

public class CartManager {

    private CartDAO cartDAO = CartDAO.getInstance();
    private LineCmdManager lineCmdManager = new LineCmdManager();
    private OrderManager orderManager = new OrderManager();
    private ProductManager productManager = new ProductManager();

    public Collection<LineCmd> getLineCmds(Cart cart){
        if(cart == null) return (new ArrayList<>());
        if(!cart.getLineCmds().isEmpty())
            return cart.getLineCmds();
        return cartDAO.getLineCmds(cart);
    }

    public Answer addCart(){

        Cart cart = new Cart(-1, null, 0.0);
        cartDAO.add(cart);

        return Answer.getInstance(true, "cart has been added successfully", cart);

    }

    public Answer updateCartTotalprice(Cart cart, double totalPrice){

        if(totalPrice <= 0)
            return Answer.getInstance(false, "total price must be positive");

        cart.setTotalPrice(totalPrice);

        return Answer.getInstance(true, "cart has been added successfully", cart);

    }

    public Answer updateCartLineCmd(int quantity, LineCmd lineCmd, Cart cart){

        if(quantity+lineCmd.getQuantity() > lineCmd.getProduct().getQuantity())
            return Answer.getInstance(false, "this quantity doesn't exist in our stock");

        double priceToAdd = lineCmd.getProduct().getSellingPrice() * quantity;
        double price = lineCmd.getPrice() + priceToAdd;
        quantity += lineCmd.getQuantity();
        lineCmdManager.updateLineCmdPrice(lineCmd, price);
        lineCmdManager.updateLineCmdQuantity(lineCmd, quantity);

        updateCartTotalprice(cart, cart.getTotalPrice() + priceToAdd);

        return Answer.getInstance(true, "cart has been added successfully", cart);

    }

    public LineCmd checkProduct(Product product, Cart cart){

        ArrayList<LineCmd> lineCmds;

        if(cart.getLineCmds().isEmpty())
            lineCmds = (ArrayList<LineCmd>) cartDAO.getLineCmds(cart);
        else
            lineCmds = (ArrayList<LineCmd>) cart.getLineCmds();

        for(LineCmd lineCmd:lineCmds){
            if(lineCmd.getProduct().getId() == product.getId())
                return lineCmd;
        }

        return null;

    }

    public Answer checkout(Address shippingAddress, Client client){

        Cart cart = client.getCart();

        Answer answer = orderManager.addOrder(cart, shippingAddress, client);

        if(!answer.isSuccess())
            return answer;

        Order order = (Order) answer.getObject();

        lineCmdManager.update(order, cart);
        productManager.updateProductsQuantity(cart.getLineCmds());

        cart.getLineCmds().clear();
        cart.setTotalPrice(0.0);

        return answer;

    }



    public Answer connectToServer(Client client, String number, String cvv, String expirationDate){

        if(number.length() != 14)
            return Answer.getInstance(false, "number name must be 14 chars");

        try{
            Integer.parseInt(cvv);
            if(cvv.length() != 3) {
                return Answer.getInstance(false, "cvv must be 3 digits");
            }
        }catch (Exception e){
            return Answer.getInstance(false, "cvv must be a number");
        }

        if(expirationDate.length() != 7)
            return Answer.getInstance(false, "you should enter a valid date format(08/2012)");

        try {
            String date[] = expirationDate.split("/");
            if(date[0].length() != 2 || date[1].length() != 4){
                return Answer.getInstance(false, "you should enter a valid date format(08/2012)");
            }
        }catch (IndexOutOfBoundsException e){
            return Answer.getInstance(false, "you should enter a valid date format(08/2012)");
        }

        try {

            Cart cart = client.getCart();

            Socket clientEndSocket = new Socket(Configuration.HOST, Configuration.PORT);
            ObjectOutputStream output = new ObjectOutputStream(clientEndSocket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(clientEndSocket.getInputStream());

            String data = number+" "+cvv+" "+expirationDate;

            Response response = Response.createRequest(data, "", false);
            output.writeObject(response);

            response = (Response) input.readObject();

            if(response.isError()){
                output.writeObject(Response.createResponse(null, "done", false));
                output.flush();

                return Answer.getInstance(false, response.getErrorMsg());
            }else {
                output.writeDouble(cart.getTotalPrice());
                output.flush();

                response = (Response) input.readObject();

                output.writeObject(Response.createResponse(null, "done", false));
                output.flush();

                if(response.isError()){
                    return Answer.getInstance(false, response.getErrorMsg());
                }else {
                    return Answer.getInstance(true, response.getSuccessMsg());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
