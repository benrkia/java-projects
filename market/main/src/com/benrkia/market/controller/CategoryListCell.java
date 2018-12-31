package com.benrkia.market.controller;

import com.benrkia.market.products.Category;
import com.jfoenix.controls.JFXListCell;

class CategoryListCell extends JFXListCell<Category> {

    @Override
    protected void updateItem(Category category, boolean empty) {
        super.updateItem(category, empty);

        if(empty || category==null){
            setText(null);
        }else {
            setText(category.getName());
        }

    }
}