package com.benrkia.wargame.component;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Component {

    protected Image idle;
    protected Node body;
    private boolean alive = true;

    public boolean isAlive() {
        return alive;
    }

    public boolean isDead(){
        return !isAlive();
    }

    protected void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Node getBody() {
        return body;
    }

    public boolean isIntersect(Component component){
        return body.getBoundsInParent().intersects(component.body.getBoundsInParent());
    }

    public void kill(Pane container){
        container.getChildren().remove(this.getBody());
        setAlive(false);
    }

}
