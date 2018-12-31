package com.benrkia.wargame.configuration;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExplosionImage {

    private Image explosionIdle;
    private static ExplosionImage explosionImage;

    private ExplosionImage(){
        try {
            explosionIdle= new Image(new FileInputStream(Configuration.BOMB_EXPLOSION_IMAGE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Image getExplosionIdle() {
        return explosionIdle;
    }

    public static ExplosionImage getInstance(){
        if(explosionImage == null)
            explosionImage = new ExplosionImage();

        return explosionImage;
    }

}
