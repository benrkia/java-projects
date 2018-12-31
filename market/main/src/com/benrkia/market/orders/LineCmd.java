package com.benrkia.market.orders;

import com.benrkia.market.products.Product;

public class LineCmd {

    private long id;
    private int quantity;
    private double price;
    private short lineCmdType;
    private Product product;
    private Order order;
    private Cart cart;

    public LineCmd(long id, int quantity, double price, short lineCmdType, Product product, Order order, Cart cart) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.lineCmdType = lineCmdType;
        this.product = product;
        setOrder(order);
        setCart(cart);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public short getLineCmdType() {
        return lineCmdType;
    }

    public void setLineCmdType(short lineCmdType) {
        this.lineCmdType = lineCmdType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        if(this.order == null){
            this.order = order;
            if(this.order != null)
                this.order.addLineCmd(this);
        }
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        if(this.cart != null)
            this.cart.removeLineCmd(this);
        this.cart = cart;
        if(this.cart != null)
            this.cart.addLineCmd(this);
    }
}
