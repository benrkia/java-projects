package com.benrkia.wargame.configuration;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BombImage {

    private Image bombIdle;
    private static BombImage bombImage;

    private BombImage(){
        try {
            bombIdle= new Image(new FileInputStream(Configuration.BOMB_IMAGE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Image getBombIdle() {
        return bombIdle;
    }

    public static BombImage getInstance(){
        if(bombImage == null)
            bombImage = new BombImage();

        return bombImage;
    }

}

