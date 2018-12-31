package com.benrkia.market.configuration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Tools {

    public static JFXTextField createJFXTextField(String promptText){

        JFXTextField jfxTextField = new JFXTextField();
        jfxTextField.setPromptText(promptText);
        jfxTextField.setLabelFloat(true);
        jfxTextField.setFocusColor(Color.web("#2196f3"));
        jfxTextField.setPadding(new Insets(5));
        jfxTextField.setFont(Font.font("System Bold", 15));

        return jfxTextField;
    }

    public static JFXComboBox createJFXComboBox(String promptText){

        JFXComboBox jfxComboBox = new JFXComboBox();
        jfxComboBox.setPromptText(promptText);
        jfxComboBox.setLabelFloat(true);
        jfxComboBox.setFocusColor(Color.web("#2196f3"));
        jfxComboBox.setPadding(new Insets(5));

        return jfxComboBox;
    }

    public static AnchorPane createAnchorPane(JFXComboBox jfxComboBox){

        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(jfxComboBox);

        AnchorPane.setTopAnchor(jfxComboBox, 0.0);
        AnchorPane.setRightAnchor(jfxComboBox, 0.0);
        AnchorPane.setBottomAnchor(jfxComboBox, 0.0);
        AnchorPane.setLeftAnchor(jfxComboBox, 0.0);

        return pane;
    }

    public static JFXCheckBox createJFXCheckBox(){

        JFXCheckBox jfxCheckBox = new JFXCheckBox();
        jfxCheckBox.setText("My main address");
        jfxCheckBox.setCheckedColor(Color.web("#2196f3"));
        jfxCheckBox.setFont(Font.font("System Bold", 15));

        return jfxCheckBox;
    }

    public static JFXButton createJFXButton(String text, String color){

        JFXButton jfxButton = new JFXButton();
        jfxButton.setStyle("-fx-background-color: "+color+";");
        jfxButton.setText(text);
        jfxButton.setTextFill(Color.WHITE);
        jfxButton.setFont(Font.font("System Bold", 15));
        jfxButton.setPadding(new Insets(10));

        return jfxButton;

    }

}
