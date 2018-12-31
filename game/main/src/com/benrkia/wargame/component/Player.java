package com.benrkia.wargame.component;

import com.benrkia.wargame.configuration.Configuration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Player extends Component{

    private static Player player;

    public static final double BODY_WIDTH = 200;
    public static final double BODY_HEIGHT = 200;
    private static final double rightRotation = - Math.PI/4;
    private static final double leftRotation = - 3*Math.PI/4;

    private Image idleLeft;
    private Image attack;
    private Image attackLeft;
    private Zone zone;
    private short lives = Configuration.PLAYER_LIVES;
    private short currentLives = Configuration.PLAYER_LIVES;
    private boolean toLeft = false;


    /*
    * For now i use the singleton design pattern
    * so i'll have just one player in the game
    * */
    private Player(Zone zone) {
        this.zone = zone;
        try {
            idle = new Image(new FileInputStream(Configuration.PLAYER_IDLE_IMAGE_PATH));
            idleLeft = new Image(new FileInputStream(Configuration.PLAYER_IDLE_LEFT_IMAGE_PATH));
            attack = new Image(new FileInputStream(Configuration.PLAYER_ATTACK_IMAGE_PATH));
            attackLeft = new Image(new FileInputStream(Configuration.PLAYER_ATTACK_LEFT_IMAGE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        body = new ImageView(idle);
        ((ImageView)body).setX(0);
        ((ImageView)body).setY(0);
        ((ImageView)body).setFitWidth(BODY_WIDTH);
        ((ImageView)body).setFitHeight(BODY_HEIGHT);
    }

    public double getX(){
        return body.getTranslateX();
    }

    public double getY(){
        return body.getTranslateY();
    }

    private void updateX(){
        body.setTranslateX((zone.getEndX()-BODY_WIDTH)/2);
    }

    private void updateY(){
        this.body.setTranslateY(zone.getEndY()-BODY_HEIGHT);
    }

    public void update(){
        updateX();
        updateY();
    }

    public static Player getInstance(Zone zone) {
        if(player == null)
            player = new Player(zone);
        return player;
    }

    public void moveToLeft() {

        ((ImageView)body).setImage(idleLeft);
        toLeft = true;

        double startX = zone.getStartX();
        double x = body.getTranslateX();
        int speed = Configuration.PLAYER_SPEED;

        if(x - startX > speed){
            body.setTranslateX(x-speed);
        }


    }

    public void moveToRight() {

        ((ImageView)body).setImage(idle);
        toLeft = false;

        double endX = zone.getEndX() - BODY_WIDTH;
        double x = body.getTranslateX();
        int speed = Configuration.PLAYER_SPEED;

        if(endX - x > speed){
            body.setTranslateX(x+speed);
        }


    }

    public Bomb shoot() {
        if(toLeft)
            ((ImageView)body).setImage(attackLeft);
        else
            ((ImageView)body).setImage(attack);

        Bomb bomb = new Bomb(this);

        return bomb;

    }

    public double getArmX(){
        if(toLeft)
            return getX()+50;
        return getX()+BODY_WIDTH-70;
    }

    public double getArmY(){
        return getY()+20;
    }

    public double getDirection(){
        if(toLeft)
            return this.leftRotation;
        return this.rightRotation;
    }
}
