package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Pane root;

    private List<Pedestrian> bullets = new ArrayList<>();
    private List<Pedestrian> enemies = new ArrayList<>();

    private Pedestrian player;

    private Parent createContent(){
        root = new Pane();
        root.setPrefSize(600, 600);

        player = new Player();
        player.setVelocety(new Point2D(1, 0));

        addPedestrian(player, 300, 300);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        return root;
    }

    private void addBullet(Pedestrian bullet, double x, double y){
        bullets.add(bullet);
        addPedestrian(bullet, x, y);
    }

    private void addEnemy(Pedestrian enemy, double x, double y){
        enemies.add(enemy);
        addPedestrian(enemy, x, y);
    }

    private void addPedestrian(Pedestrian pedestrian, double x, double y){
        pedestrian.getView().setTranslateX(x);
        pedestrian.getView().setTranslateY(y);
        root.getChildren().add(pedestrian.getView());
    }
    //runs 60 times/sec
    private void onUpdate(){
        for(Pedestrian bullet : bullets){
            for(Pedestrian enemy : enemies){
                if(bullet.isColliding(enemy)){
                    bullet.setAlive(false);
                    enemy.setAlive(false);

                    root.getChildren().removeAll(bullet.getView(), enemy.getView());
                }
            }
        }
        bullets.removeIf(Pedestrian::isDead);
        enemies.removeIf(Pedestrian::isDead);

        bullets.forEach(Pedestrian::update);
        enemies.forEach(Pedestrian::update);

        player.update();

        if(Math.random() < 0.02){
            addEnemy(new Enemy(), Math.random() * root.getPrefWidth(),
                    Math.random() * root.getPrefHeight());
        }
    }

    private static class Player extends Pedestrian{
        Player(){
            super(new Rectangle(40, 20, Color.BLUE));
        }
    }

    private static class Enemy extends Pedestrian{
        Enemy(){
            super(new Circle(15, 15, 15, Color.RED));
        }
    }

    private static class Bullet extends Pedestrian{
        Bullet(){
            super(new Circle(5, 5, 5, Color.BROWN));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.getScene().setOnKeyPressed( event -> {
            if(event.getCode() == KeyCode.LEFT){
                player.rotateLeft();
            }
            else if(event.getCode() == KeyCode.RIGHT){
                player.rotateRight();
            }
            else if(event.getCode() == KeyCode.SPACE){
                Bullet bullet = new Bullet();
                bullet.setVelocety(player.getVelocety().normalize().multiply(5));
                addBullet(bullet, player.getView().getTranslateX(),
                        player.getView().getTranslateY());
            }
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
