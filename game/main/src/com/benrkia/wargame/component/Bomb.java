package com.benrkia.wargame.component;

import com.benrkia.wargame.configuration.Configuration;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

public class Bomb extends Component{

    private Point2D direction;
    private static final double BODY_WIDTH = 20;
    private static final double BODY_HEIGHT = 20;

    public Bomb(Player player) {
        idle = Configuration.getBombImage();

        body = new ImageView(idle);
        ((ImageView)body).setX(player.getArmX());
        ((ImageView)body).setY(player.getArmY());
        ((ImageView)body).setFitWidth(BODY_WIDTH);
        ((ImageView)body).setFitHeight(BODY_HEIGHT);

        setDirection(player.getDirection());
    }

    public void setDirection(double angle) {
        double x = Math.cos(angle);
        double y = Math.sin(angle);
        direction = new Point2D(x, y).normalize().multiply(Configuration.BOMB_SPEED);
    }

    public void move() {

        body.setTranslateX(body.getTranslateX() + direction.getX());
        body.setTranslateY(body.getTranslateY() + direction.getY());

    }
}
