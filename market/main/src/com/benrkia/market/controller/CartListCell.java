package com.benrkia.market.controller;

import com.benrkia.market.orders.LineCmd;
import com.jfoenix.controls.JFXListCell;

class CartListCell extends JFXListCell<LineCmd> {

    @Override
    protected void updateItem(LineCmd lineCmd, boolean empty) {
        super.updateItem(lineCmd, empty);

        if(empty || lineCmd==null){
            setText(null);
        }else {
            String text = lineCmd.getQuantity()+"x "+lineCmd.getProduct().getName()+" : "+String.format("$%.2f", lineCmd.getPrice());

            setText(text);
        }

    }
}