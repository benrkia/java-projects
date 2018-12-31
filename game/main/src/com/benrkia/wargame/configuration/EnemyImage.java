package com.benrkia.wargame.configuration;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EnemyImage {

    private Image enemyIdle;
    private static EnemyImage enemyImage;

    private EnemyImage(){
        try {
             enemyIdle= new Image(new FileInputStream(Configuration.ENEMY_IMAGE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Image getEnemyIdle() {
        return enemyIdle;
    }

    public static EnemyImage getInstance(){
        if(enemyImage == null)
            enemyImage = new EnemyImage();

        return enemyImage;
    }

}
