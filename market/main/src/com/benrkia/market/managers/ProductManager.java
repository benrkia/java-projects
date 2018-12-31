package com.benrkia.market.managers;

import com.benrkia.market.configuration.Answer;
import com.benrkia.market.dao.ProductDAO;
import com.benrkia.market.orders.LineCmd;
import com.benrkia.market.products.Category;
import com.benrkia.market.products.Image;
import com.benrkia.market.products.Product;
import com.benrkia.market.users.Commercial;

import java.util.Collection;

public class ProductManager {

    private ProductDAO productDAO = ProductDAO.getInstance();

    public Collection<Product> getNewArrivals() {
        return productDAO.getNewArrivals();
    }

    public Collection<Product> getTopSaled() {
        return productDAO.getTopSaled();
    }

    public Answer addProduct(String name, String description, double sellingPrice, double buyingPrice, int quantity, Commercial commercial, Category category, Image image){

        if(name.length() < 5)
            return Answer.getInstance(false, "name must be at least 5 chars");
        if(description.length() < 10)
            return Answer.getInstance(false, "description must be at least 10 chars");
        if(sellingPrice <= 0 || buyingPrice <= 0)
            return Answer.getInstance(false, "price must be positive");
        if(buyingPrice >= sellingPrice)
            return Answer.getInstance(false, "selling price must be greater than buying price");
        if(quantity <= 0)
            return Answer.getInstance(false, "quantity must be positive");

        Product product = new Product(-1, name, description, sellingPrice, buyingPrice, quantity, null, commercial, category, image);
        productDAO.add(product);

        return Answer.getInstance(true, "product has been added successfully", product);

    }

    public Answer updateProductName(Product product, String name){

        if(name.length() < 5)
            return Answer.getInstance(false, "name must be at least 5 chars");

        product.setName(name);
        productDAO.update(product);

        return Answer.getInstance(true, "product has been updated successfully", product);
    }

    public Answer updateProductDescription(Product product, String description){

        if(description.length() < 10)
            return Answer.getInstance(false, "description must be at least 10 chars");

        product.setDescription(description);
        productDAO.update(product);

        return Answer.getInstance(true, "product has been updated successfully", product);
    }

    public Answer updateProductQuantity(Product product, int quantity){

        if(quantity <= 0)
            return Answer.getInstance(false, "quantity must be positive");

        product.setQuantity(quantity);
        productDAO.update(product);

        return Answer.getInstance(true, "product has been updated successfully", product);
    }

    public Answer updateProductSellingPrice(Product product, double sellingPrice){

        if(sellingPrice <= 0)
            return Answer.getInstance(false, "price must be positive");
        if(product.getBuyingPrice() >= sellingPrice)
            return Answer.getInstance(false, "selling price must be greater than buying price");

        product.setSellingPrice(sellingPrice);
        productDAO.update(product);

        return Answer.getInstance(true, "product has been updated successfully", product);
    }

    public Answer updateProductCategory(Product product, Category category){

        product.setCategory(category);
        productDAO.update(product);

        return Answer.getInstance(true, "product has been updated successfully", product);
    }

    public Answer updateProductImage(Product product, Image image){

        product.setImage(image);
        productDAO.update(product);

        return Answer.getInstance(true, "product has been updated successfully", product);
    }

    public Answer deleteProduct(Product product){

        productDAO.delete(product);

        return Answer.getInstance(true, "product has been deleted successfully");

    }

    public void updateProductsQuantity(Collection<LineCmd> lineCmds){
        for (LineCmd lineCmd : lineCmds){
            this.updateProductQuantity(lineCmd.getProduct(), lineCmd.getProduct().getQuantity() - lineCmd.getQuantity());
        }
    }

}
