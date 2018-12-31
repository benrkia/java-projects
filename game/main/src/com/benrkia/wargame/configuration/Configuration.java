package com.benrkia.wargame.configuration;

import javafx.scene.image.Image;

public final class Configuration {

    public static final String BACKGROUND_IMAGE_PATH = "assets/bg.jpg";
    public static final String PLAYER_IDLE_IMAGE_PATH = "assets/player.png";
    public static final String PLAYER_IDLE_LEFT_IMAGE_PATH = "assets/player_left.png";
    public static final String PLAYER_ATTACK_IMAGE_PATH = "assets/attack.png";
    public static final String PLAYER_ATTACK_LEFT_IMAGE_PATH = "assets/attack_left.png";
    public static final String ENEMY_IMAGE_PATH = "assets/enemy.png";
    public static final String BOMB_IMAGE_PATH = "assets/bomb.png";
    public static final String BOMB_EXPLOSION_IMAGE_PATH = "assets/explosion.gif";

    public static final short ENEMY_SPEED = 2;
    public static final short PLAYER_SPEED = 10;
    public static final short BOMB_SPEED = 5;
    public static final short ENEMY_LIVES = 2;
    public static final short PLAYER_LIVES = 4;
    public static final short BLOOD_LINE_HEIGHT = 2;

    // enemy zone percentage of the scene total height;
    public static final double ENEMY_ZONE_PERCENTAGE = 0.5;


    // determine the max possible number of enemies in one time
    public static final short MAX_ENEMY_COUNT = 5;
    public static short currentEnemyCount = 0;

    public static double containerWidth = 0;
    public static double containerHeight = 0;


    public static double enemyZoneEndY = 0;

    public static Image getEnemyImage() {
        return EnemyImage.getInstance().getEnemyIdle();
    }
    public static Image getBombImage() {
        return BombImage.getInstance().getBombIdle();
    }
    public static Image getExplosionImage() {
        return ExplosionImage.getInstance().getExplosionIdle();
    }
}
