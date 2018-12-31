package com.benrkia.market.controller;

import com.benrkia.market.products.Product;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ListViewCell extends JFXListCell<Product> {

    @FXML
    private AnchorPane container;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);

        if(empty || product==null){
            setText(null);
            setGraphic(null);
        }else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/ListViewCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                productImage.setImage(new Image(new FileInputStream("assets/"+product.getImage().getName()+"."+product.getImage().getExtension())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            productName.setText(product.getName());
            productPrice.setText("$"+product.getSellingPrice());

            setText(null);
            setGraphic(container);
        }

    }
}
