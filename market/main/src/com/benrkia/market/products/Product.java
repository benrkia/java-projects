package com.benrkia.market.products;

import com.benrkia.market.users.Commercial;

import java.util.Date;

public class Product {

    private long id;
    private String name;
    private String description;
    private double sellingPrice;
    private double buyingPrice;
    private int quantity;
    private Date creationDate;
    private Commercial commercial;
    private Category category;
    private Image image;

    public Product(long id, String name, String description, double sellingPrice, double buyingPrice, int quantity, Date creationDate, Commercial commercial, Category category, Image image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.quantity = quantity;
        this.creationDate = creationDate;
        this.image = image;
        setCommercial(commercial);
        setCategory(category);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if(this.category != null)
            this.category.removeProduct(this);
        this.category = category;
        if(this.category != null)
            this.category.addProduct(this);
    }

    public Commercial getCommercial() {
        return commercial;
    }

    public void setCommercial(Commercial commercial) {
        if(this.commercial == null){
            this.commercial = commercial;
            if(this.commercial != null)
                this.commercial.addProduct(this);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
