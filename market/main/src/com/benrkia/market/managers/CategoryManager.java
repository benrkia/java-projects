package com.benrkia.market.managers;

import com.benrkia.market.configuration.Answer;
import com.benrkia.market.dao.CategoryDAO;
import com.benrkia.market.products.Category;
import com.benrkia.market.products.Product;

import java.util.Collection;

public class CategoryManager {

    private CategoryDAO categoryDAO = CategoryDAO.getInstance();

    public Collection<Category> getAll(){
        return categoryDAO.getAll();
    }

    public Collection<Product> getProducts(Category category){
        if(!category.getProducts().isEmpty())
            return category.getProducts();
        return categoryDAO.getProducts(category);
    }

    public Answer addCategory(String name, String description){

        if(name.length() < 5)
            return Answer.getInstance(false, "name must be at least 5 chars");
        if(description.length() < 10)
            return Answer.getInstance(false, "description must be at least 10 chars");

        Category category = new Category(-1, name, description);
        categoryDAO.add(category);

        return Answer.getInstance(true, "category has been added successfully", category);

    }

    public Answer updateCategoryName(Category category, String name){

        if(name.length() < 5)
            return Answer.getInstance(false, "name must be at least 5 chars");

        category.setName(name);
        categoryDAO.update(category);

        return Answer.getInstance(true, "category has been updated successfully", category);
    }

    public Answer updateCategoryDescription(Category category, String description){

        if(description.length() < 10)
            return Answer.getInstance(false, "description must be at least 10 chars");

        category.setDescription(description);
        categoryDAO.update(category);

        return Answer.getInstance(true, "category has been updated successfully", category);
    }

}
