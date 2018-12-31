package com.benrkia.wargame;

import com.benrkia.wargame.component.*;
import com.benrkia.wargame.configuration.Configuration;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    private AnchorPane board;
    private Pane top;
    private Pane container;
    private Image bgImage;
    private Zone enemyZone;
    private Zone playerZone;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bomb> bombs = new ArrayList<>();
    private AnimationTimer animationTimer;

    /*
    * This function set the game board by:
    * Setting the background
    * Setting all the components of the game
    * */
    private void setGameBoard(Stage primaryStage) throws FileNotFoundException {

        board = new AnchorPane();
        top = new Pane();

        container = new Pane();

        bgImage = new Image(new FileInputStream(Configuration.BACKGROUND_IMAGE_PATH));

        enemyZone = new Zone();
        playerZone = new Zone();


        player = Player.getInstance(playerZone);


        container.widthProperty().addListener((observable, oldValue, newValue) -> {
            Configuration.containerWidth = container.getWidth();
            enemyZone.setEndX(Configuration.containerWidth);
            playerZone.setEndX(Configuration.containerWidth);

            player.update();

            container.setBackground(new Background(new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(Configuration.containerWidth, Configuration.containerHeight, false, false, false, false))));
        });

        container.heightProperty().addListener((observable, oldValue, newValue) -> {
            Configuration.containerHeight = container.getHeight();
            Configuration.enemyZoneEndY = Configuration.containerHeight*Configuration.ENEMY_ZONE_PERCENTAGE;
            enemyZone.setEndY(Configuration.enemyZoneEndY);
            playerZone.setStartY(Configuration.enemyZoneEndY);
            playerZone.setEndY(Configuration.containerHeight);

            player.update();

            container.setBackground(new Background(new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(Configuration.containerWidth, Configuration.containerHeight, false, false, false, false))));
        });

        container.getChildren().add(player.getBody());

        AnchorPane.setTopAnchor(top, 0.0);
        AnchorPane.setLeftAnchor(top, 0.0);
        AnchorPane.setRightAnchor(top, 0.0);

        AnchorPane.setTopAnchor(container, 0.0);
        AnchorPane.setBottomAnchor(container, 0.0);
        AnchorPane.setLeftAnchor(container, 0.0);
        AnchorPane.setRightAnchor(container, 0.0);

        board.getChildren().addAll(container);
        Scene root = new Scene(board);
        primaryStage.setScene(root);
        primaryStage.setFullScreen(true);
    }

    /*
    * This function set the enemies
    * */
    private void setEnemies(){

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                for(Enemy enemy:enemies){
                    enemy.move();
                }

                for(Bomb bomb:bombs){
                    bomb.move();
                }

                for (Bomb bomb:bombs){
                    for (Enemy enemy:enemies){
                        if (bomb.isAlive() && bomb.isIntersect(enemy)){
                            enemy.kill(container);
                            bomb.kill(container);
                        }
                    }
                }

                bombs.removeIf(Component::isDead);
                enemies.removeIf(Component::isDead);

                addEnemy();
            }
        };
        animationTimer.start();

    }

    /*
    * function that adds new enemy to the game
    * */
    private void addEnemy() {

        if(Configuration.currentEnemyCount<Configuration.MAX_ENEMY_COUNT && Math.random()<0.01){
            Enemy enemy = new Enemy(enemyZone);
            container.getChildren().add(enemy.getBody());
            ++Configuration.currentEnemyCount;

            enemies.add(enemy);
        }

    }

    /*
    * action handler
    *
    * */
    private void actionHandler(Scene root){

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT){
                player.moveToLeft();
            }
            if (event.getCode() == KeyCode.RIGHT){
                player.moveToRight();
            }
            if (event.getCode() == KeyCode.SPACE){
                Bomb bomb = player.shoot();
                container.getChildren().add(bomb.getBody());
                bombs.add(bomb);
            }
        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        setGameBoard(primaryStage);

        setEnemies();

        actionHandler(primaryStage.getScene());

        primaryStage.show();

    }

    public static void main(String args[]){

        launch(args);

    }
}
