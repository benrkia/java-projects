package com.benrkia.market.controller;

import com.benrkia.bank.Response;
import com.benrkia.market.address.Address;
import com.benrkia.market.address.City;
import com.benrkia.market.address.Country;
import com.benrkia.market.configuration.Answer;
import com.benrkia.market.configuration.Configuration;
import com.benrkia.market.configuration.Tools;
import com.benrkia.market.managers.*;
import com.benrkia.market.orders.Cart;
import com.benrkia.market.orders.LineCmd;
import com.benrkia.market.products.Category;
import com.benrkia.market.products.Product;
import com.benrkia.market.users.Client;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class Dashboard {

    @FXML
    private ImageView logo;
    
    @FXML
    private ImageView cart;

    @FXML
    private ImageView productImage;

    @FXML
    private JFXListView<Category> categoryList;

    @FXML
    private JFXListView<Product> newArrivals;

    @FXML
    private JFXListView<Product> topSaled;

    @FXML
    private JFXListView<LineCmd> cartLineCmds;

    @FXML
    private Pane homePane;

    @FXML
    private Label homeButton;

    @FXML
    private Label newArrivalsLabel;

    @FXML
    private Label productName;

    @FXML
    private Label cartTotalPrice;

    @FXML
    private Label productPrice;

    @FXML
    private Label productTotalPrice;

    @FXML
    private JFXButton username;

    @FXML
    private JFXButton productQuantityPlus;

    @FXML
    private JFXButton productQuantityMinus;

    @FXML
    private JFXButton addToCart;

    @FXML
    private JFXButton checkout;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane productPane;

    @FXML
    private AnchorPane cartPane;

    @FXML
    private JFXTextArea productDesctiption;

    @FXML
    private JFXTextField productQuantity;

    private Popup popup;


    private PersonManager personManager = new PersonManager();
    private CategoryManager categoryManager = new CategoryManager();
    private ProductManager productManager = new ProductManager();
    private CartManager cartManager = new CartManager();
    private LineCmdManager lineCmdManager = new LineCmdManager();
    private CountryManager countryManager = new CountryManager();
    private AddressManager addressManager = new AddressManager();

    private Client client = (Client) personManager.get(8);

    private Product currentProduct = null;

    private ObservableList<Category> categoryObservable = FXCollections.observableArrayList(categoryManager.getAll());
    private ObservableList<Product> newArrivalsObservable = FXCollections.observableArrayList(productManager.getNewArrivals());
    private ObservableList<Product> topSaledObservable = FXCollections.observableArrayList(productManager.getTopSaled());

    @FXML
    public void initialize(){
        /* add a simple rippler to the home button */
        JFXRippler rippler = new JFXRippler(homeButton);
        homePane.getChildren().add(rippler);

        /* init the popup window and username */
        username.setText(client.toString());
        initPopup();
        username.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
            if(popup.isShowing()){
                popup.hide();
            }else {
                popup.show(username, event.getScreenX(), event.getScreenY());
            }
        });


        /* init the logo and the cart image */
        try {
            logo.setImage(new Image(new FileInputStream("assets/logo.png")));
            cart.setImage(new Image(new FileInputStream("assets/cart.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /* init & control the category menu */
        categoryList.getItems().addAll(categoryObservable);
        categoryList.setCellFactory(param -> new CategoryListCell());
        categoryList.setOnMouseClicked(event -> {
            Category category = categoryList.getSelectionModel().getSelectedItem();
            newArrivalsLabel.setText(category.getName());
            newArrivals.getItems().setAll(FXCollections.observableArrayList(categoryManager.getProducts(category)));

            showMainPane();
        });

        /* init & control the top saled menu */
        topSaled.getItems().addAll(topSaledObservable);
        topSaled.setCellFactory(param -> new ListViewCell());
        topSaled.setOnMouseClicked(event -> {
            currentProduct = topSaled.getSelectionModel().getSelectedItem();
            productQuantity.setText("0");
            productName.setText(currentProduct.getName());
            productDesctiption.setText(currentProduct.getDescription());
            productPrice.setText("$"+currentProduct.getSellingPrice());
            productTotalPrice.setText("$0.00");
            try {
                productImage.setImage(new Image(new FileInputStream("assets/"+currentProduct.getImage().getName()+"."+currentProduct.getImage().getExtension())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            showProductPane();

        });

        /* init & control the new Arrivals (which is also the category section) menu */
        newArrivals.getItems().addAll(newArrivalsObservable);
        newArrivals.setCellFactory(param -> new ListViewCell());
        newArrivals.setOnMouseClicked(event -> {
            currentProduct = newArrivals.getSelectionModel().getSelectedItem();
            productQuantity.setText("0");
            productName.setText(currentProduct.getName());
            productDesctiption.setText(currentProduct.getDescription());
            productPrice.setText("$"+currentProduct.getSellingPrice());
            productTotalPrice.setText("$0.00");
            try {
                productImage.setImage(new Image(new FileInputStream("assets/"+currentProduct.getImage().getName()+"."+currentProduct.getImage().getExtension())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            showProductPane();
        });

        /* init & control the client's cart */
        cartLineCmds.getItems().addAll(FXCollections.observableArrayList(cartManager.getLineCmds(client.getCart())));
        cartLineCmds.setCellFactory(param -> new CartListCell());
        if(client.getCart() != null)
            cartTotalPrice.setText(String.format("$%.2f", client.getCart().getTotalPrice()));
        cartLineCmds.setOnMouseClicked(event -> {
            LineCmd lineCmd = cartLineCmds.getSelectionModel().getSelectedItem();
            if(lineCmd != null && event.getClickCount() == 2){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("You really want to delete this line item ??");

                ButtonType Yes = new ButtonType("Yes");
                ButtonType Cancel = new ButtonType("Cancel");
                alert.getButtonTypes().setAll(Yes, Cancel);

                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == Yes){
                    Answer answer = lineCmdManager.removeLineCmd(lineCmd);
                    if(answer.isSuccess()){
                        //381.58
                        cartLineCmds.getItems().setAll(FXCollections.observableArrayList(cartManager.getLineCmds(client.getCart())));
                        cartTotalPrice.setText(String.format("$%.2f", client.getCart().getTotalPrice()));
                    }
                }
            }
        });

        cart.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            showCartPane();
        });

        /* init & control the home Button */
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            newArrivalsLabel.setText("New Arrivals");
            newArrivals.getItems().setAll(newArrivalsObservable);
            categoryList.getSelectionModel().clearSelection();

            showMainPane();
        });

        /* control the product Quantity Plus&Minus Button */
        productQuantityPlus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int quantity = Integer.parseInt(productQuantity.getText());
            if(quantity < currentProduct.getQuantity())
                ++quantity;
            productQuantity.setText(quantity+"");
            productTotalPrice.setText(String.format("$%.2f", (quantity * currentProduct.getSellingPrice())));

        });

        productQuantityMinus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int quantity = Integer.parseInt(productQuantity.getText());
            if(quantity > 0)
                --quantity;
            productQuantity.setText(quantity+"");
            productTotalPrice.setText(String.format("$%.2f", (quantity * currentProduct.getSellingPrice())));

        });

        /* add to cart control */
        addToCart.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Answer answer;
            Alert alert;

            int quantity = Integer.parseInt(productQuantity.getText());

            if(quantity == 0){
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Add 0 product to the cart");
                alert.setContentText("You can't add 0 product to the cart");
                alert.showAndWait();
                return;
            }

            if(client.getCart() == null) {
                answer = cartManager.addCart();
                if(answer.isSuccess()) {
                    personManager.updateCart(client, (Cart) answer.getObject());
                }
            }

            LineCmd lineCmd = cartManager.checkProduct(currentProduct, client.getCart());
            if(lineCmd == null){
                answer = lineCmdManager.addLineCmd(quantity, currentProduct, client.getCart());
            }else {
                answer = cartManager.updateCartLineCmd(quantity, lineCmd, client.getCart());
            }

            if(!answer.isSuccess()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error while adding to the cart");
                alert.setContentText(answer.getMessage());

                alert.showAndWait();
            }
            else {
                cartLineCmds.getItems().setAll(FXCollections.observableArrayList(cartManager.getLineCmds(client.getCart())));
                cartTotalPrice.setText(String.format("$%.2f", client.getCart().getTotalPrice()));
                showCartPane();
            }


        });

        /* checkout control */
        checkout.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            if(client.getCart() == null || client.getCart().getLineCmds().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("You must add some products to your cart");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Checkout");
                alert.setContentText("please confirm");

                ButtonType Yes = new ButtonType("Yes");
                ButtonType Cancel = new ButtonType("Cancel");
                alert.getButtonTypes().setAll(Yes, Cancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == Yes) {

                    addAddress();

                }
            }

        });

    }

    public void addAddress() {

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Add the shipping address");

        VBox vBox = new VBox();
        vBox.setMinWidth(300);
        vBox.setPrefWidth(300);
        vBox.setMinHeight(400);


        Label errorMessage = new Label();
        errorMessage.setTextFill(Color.RED);
        vBox.setMargin(errorMessage, new Insets(30, 10, 0, 10));


        JFXTextField street = Tools.createJFXTextField("Street");
        vBox.setMargin(street, new Insets(20, 10, 0, 10));
        street.setOnKeyReleased(event -> {
            if(street.getText().length() < 3){
                errorMessage.setText("street name must be at least 3 chars");
            }
            else {
                errorMessage.setText("");
            }
        });

        JFXTextField postalCode = Tools.createJFXTextField("Postal Code");
        vBox.setMargin(postalCode, new Insets(30, 10, 0, 10));
        postalCode.setOnKeyReleased(event -> {
            try{
                Integer.parseInt(postalCode.getText());
                if(postalCode.getText().length() < 4) {
                    errorMessage.setText("postal code must be at least 4 digits");
                }else {
                    errorMessage.setText("");
                }
            }catch (Exception e){
                errorMessage.setText("postal code must be a number");
            }
        });


        JFXComboBox<City> cityJFXComboBox = Tools.createJFXComboBox("City");

        JFXComboBox<Country> countryJFXComboBox = Tools.createJFXComboBox("Country");
        countryJFXComboBox.getItems().addAll(countryManager.getAll());
        countryJFXComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            cityJFXComboBox.getSelectionModel().clearSelection();
            cityJFXComboBox.getItems().setAll(countryManager.getCities(newValue));
        });


        AnchorPane countryAnchorPane = Tools.createAnchorPane(countryJFXComboBox);
        vBox.setMargin(countryAnchorPane, new Insets(30, 10, 0, 10));

        AnchorPane cityAnchorPane = Tools.createAnchorPane(cityJFXComboBox);
        vBox.setMargin(cityAnchorPane, new Insets(30, 10, 0, 10));


        JFXCheckBox useMyAddress = Tools.createJFXCheckBox();
        vBox.setMargin(useMyAddress, new Insets(30, 10, 0, 10));
        useMyAddress.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue && client.getAddress() != null){
                street.setDisable(true);
                postalCode.setDisable(true);
                countryJFXComboBox.setDisable(true);
                cityJFXComboBox.setDisable(true);
            }else {
                street.setDisable(false);
                postalCode.setDisable(false);
                countryJFXComboBox.setDisable(false);
                cityJFXComboBox.setDisable(false);
            }
        });


        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BOTTOM_RIGHT);


        JFXButton close = Tools.createJFXButton("Close", "#5a5a5a");
        hBox.setMargin(close, new Insets(30, 10, 10, 10));
        close.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> popupwindow.close());

        JFXButton submit = Tools.createJFXButton("Submit", "#2196F3");
        hBox.setMargin(submit, new Insets(30, 10, 10, 10));
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Address shippingAddress;
            Answer answer = Answer.getInstance(true, "");
            Country country = countryJFXComboBox.getSelectionModel().getSelectedItem();
            City city = cityJFXComboBox.getSelectionModel().getSelectedItem();

            if(useMyAddress.isSelected() && client.getAddress() != null){
                shippingAddress = client.getAddress();
            }else {
                answer = addressManager.addAddress(street.getText(), city, country, postalCode.getText());
                shippingAddress = (Address) answer.getObject();

                if(useMyAddress.isSelected() && client.getAddress() == null){
                    personManager.updateAddress(client, shippingAddress);
                }
            }

            if(!answer.isSuccess())
                errorMessage.setText(answer.getMessage());
            else {
                this.verifyCardInfo(shippingAddress);
                popupwindow.close();
            }

        });


        hBox.getChildren().addAll(close, submit);


        vBox.getChildren().addAll(street, postalCode, countryAnchorPane, cityAnchorPane, useMyAddress, errorMessage, hBox);

        Scene scene1= new Scene(vBox);

        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }

    private void verifyCardInfo(Address shippingAddress){

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Enter your card info");

        VBox vBox = new VBox();
        vBox.setMinWidth(300);
        vBox.setPrefWidth(300);
        vBox.setMinHeight(300);


        Label errorMessage = new Label();
        errorMessage.setTextFill(Color.RED);
        vBox.setMargin(errorMessage, new Insets(30, 10, 0, 10));


        JFXTextField number = Tools.createJFXTextField("Number");
        vBox.setMargin(number, new Insets(20, 10, 0, 10));
        number.setOnKeyReleased(event -> {
            if(number.getText().length() != 14){
                errorMessage.setText("number name must be 14 chars");
            }
            else {
                errorMessage.setText("");
            }
        });

        JFXTextField cvv = Tools.createJFXTextField("Cvv");
        vBox.setMargin(cvv, new Insets(30, 10, 0, 10));
        cvv.setOnKeyReleased(event -> {
            try{
                Integer.parseInt(cvv.getText());
                if(cvv.getText().length() != 3) {
                    errorMessage.setText("cvv must be 3 digits");
                }else {
                    errorMessage.setText("");
                }
            }catch (Exception e){
                errorMessage.setText("cvv must be a number");
            }
        });

        JFXTextField expirationDate = Tools.createJFXTextField("Expiration Date (08/2012)");
        vBox.setMargin(expirationDate, new Insets(30, 10, 0, 10));
        expirationDate.setOnKeyReleased(event -> {
            if(expirationDate.getText().length() != 7) {
                errorMessage.setText("you should enter a valid date format(08/2012)");
                try {
                    String date[] = expirationDate.getText().split("/");
                    if(date[0].length() != 2 || date[1].length() != 4){
                        errorMessage.setText("you should enter a valid date format(08/2012)");
                    }
                }catch (IndexOutOfBoundsException e){
                    errorMessage.setText("you should enter a valid date format(08/2012)");
                }
            }else {
                errorMessage.setText("");
            }
        });


        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BOTTOM_RIGHT);


        JFXButton close = Tools.createJFXButton("Close", "#5a5a5a");
        hBox.setMargin(close, new Insets(30, 10, 10, 10));
        close.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> popupwindow.close());

        JFXButton submit = Tools.createJFXButton("Submit", "#2196F3");
        hBox.setMargin(submit, new Insets(30, 10, 10, 10));
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            Answer answer = cartManager.connectToServer(client, number.getText(), cvv.getText(), expirationDate.getText());
            if(answer == null)
                errorMessage.setText("error");
            else if(!answer.isSuccess()){
                errorMessage.setText(answer.getMessage());
            }
            else {
                answer = cartManager.checkout(shippingAddress, client);
                if(!answer.isSuccess())
                    errorMessage.setText(answer.getMessage());
                else {

                    cartLineCmds.getItems().setAll(FXCollections.observableArrayList(cartManager.getLineCmds(client.getCart())));
                    cartTotalPrice.setText(String.format("$%.2f", client.getCart().getTotalPrice()));

                    popupwindow.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Congrats");
                    alert.setContentText("Your order has been made");
                    alert.showAndWait();

                }
            }


        });


        hBox.getChildren().addAll(close, submit);


        vBox.getChildren().addAll(number, cvv, expirationDate, errorMessage, hBox);

        Scene scene1= new Scene(vBox);

        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }

    private void showProductPane() {

        productPane.setVisible(true);
        mainPane.setVisible(false);
        cartPane.setVisible(false);

    }

    private void showMainPane() {

        mainPane.setVisible(true);
        productPane.setVisible(false);
        cartPane.setVisible(false);

    }

    private void showCartPane() {

        cartPane.setVisible(true);
        mainPane.setVisible(false);
        productPane.setVisible(false);

    }

    private void initPopup() {

        popup = new Popup();
        try {

            VBox content = FXMLLoader.load(getClass().getResource("../fxml/userPopup.fxml"));
            popup.getContent().add(content);
            popup.setAutoFix(true);
            popup.setAutoHide(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void closeDashboard(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
