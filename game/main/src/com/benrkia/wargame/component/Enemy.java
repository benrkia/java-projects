package com.benrkia.wargame.component;

import com.benrkia.wargame.configuration.Configuration;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Enemy extends Component{

    public static final double BODY_WIDTH = 150;
    public static final double BODY_HEIGHT = 150;
    private Image explosion;
    private Zone zone;
    private HBox blood;
    private short lives = Configuration.ENEMY_LIVES;
    private short currentLives = Configuration.ENEMY_LIVES;
    private boolean toLeft;
    private boolean toBottom;


    public Enemy(Zone zone) {
        this.zone = zone;

        explosion = Configuration.getExplosionImage();
        idle = Configuration.getEnemyImage();

        body = new VBox();

        // init the blood line
        blood = new HBox(lives);
        blood.setSpacing(0);
        blood.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));

        AnchorPane bloodContainer = new AnchorPane(blood);
        bloodContainer.setPrefHeight(Configuration.BLOOD_LINE_HEIGHT);
        AnchorPane.setTopAnchor(blood, 0.0);
        AnchorPane.setLeftAnchor(blood, BODY_WIDTH*0.2);
        AnchorPane.setBottomAnchor(blood, 0.0);
        AnchorPane.setRightAnchor(blood, BODY_WIDTH*0.2);


        // init the enemy image
        ImageView enemyImageView = new ImageView(idle);
        enemyImageView.setFitWidth(BODY_WIDTH);
        enemyImageView.setFitHeight(BODY_HEIGHT);


        // setup the the whole enemy body
        ((VBox)body).getChildren().addAll(bloodContainer, enemyImageView);
        ((VBox)body).setMargin(bloodContainer, new Insets(0, 0, 5, 0));


        // setup the blood line
        setUpBlood();


        // randomize the enemy position
        randomizePosition();

    }

    private void randomizePosition(){
        double x = zone.getStartX()+(zone.getEndX()-zone.getStartX()-BODY_WIDTH)*Math.random();
        double y = zone.getStartY()+(zone.getEndY()-zone.getStartY()-BODY_HEIGHT)*Math.random();
        body.setTranslateX(x);
        body.setTranslateY(y);

        boolean[] init = {true, false};
        toLeft = init[(int)Math.random()];
        toBottom = init[(int)Math.random()];
    }

    private void setUpBlood(){
        for(int i = 0; i< lives; ++i){
            Pane bloodUnity = new Pane();
            bloodUnity.setPrefWidth(BODY_WIDTH*0.6/ lives);
            bloodUnity.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            blood.getChildren().add(bloodUnity);
        }
    }

    private void updateBlood(){
        blood.getChildren().clear();
        for(int i = 0; i< currentLives; ++i){
            Pane bloodUnity = new Pane();
            bloodUnity.setPrefWidth(BODY_WIDTH*0.6/ lives);
            bloodUnity.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            blood.getChildren().add(bloodUnity);
        }
    }

    public void move(){

        double startX = zone.getStartX();
        double startY = zone.getStartY();
        double endX = zone.getEndX() - BODY_WIDTH;
        double endY = zone.getEndY() - BODY_HEIGHT;
        int speed = Configuration.ENEMY_SPEED;

        double x = body.getTranslateX();
        double y = body.getTranslateY();

        if(toLeft){
            if(x-startX > speed){
                x -= speed;
            }else{
                toLeft = false;
            }
        }else {
            if(endX-x > speed){
                x += speed;
            }else{
                toLeft = true;
            }
        }

        if(toBottom){
            if(endY-y > speed){
                y += speed;
            }else{
                toBottom = false;
            }
        }else {
            if(y-startY > speed){
                y -= speed;
            }else{
                toBottom = true;
            }
        }

        this.body.setTranslateX(x);
        this.body.setTranslateY(y);

    }

    @Override
    public void kill(Pane container){
        --currentLives;
        updateBlood();

        if(currentLives == 0) {
            super.kill(container);

            new Thread(() -> {
                ImageView imageView = new ImageView(explosion);
                imageView.setFitWidth(BODY_WIDTH);
                imageView.setFitHeight(BODY_HEIGHT);
                imageView.setTranslateX(getBody().getTranslateX());
                imageView.setTranslateY(getBody().getTranslateY());

                Platform.runLater(() -> {
                    container.getChildren().add(imageView);
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { }

                Platform.runLater(() -> {
                    container.getChildren().remove(imageView);
                });

            }).start();

            Configuration.currentEnemyCount--;
        }
    }
}
