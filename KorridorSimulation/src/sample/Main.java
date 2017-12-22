package sample;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Main extends Application {

    private final int radius = 20;
    private final double blueX = 100, redX = 1100;
    private  double blueRandY, redRandY;
    private Integer collisionCounter = 0, goalCounter = 0, idCounter = 0;
    private GraphicsContext g;
    private Pane root;
    private Canvas canvas;
    private List<Pedestrian> pedestrians = new ArrayList<>();
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            onUpdate();
        }
    };
    private Parent createContent(){
        root = new Pane();
        canvas = new Canvas(1200, 300);
        g = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        render();

        return root;
    }

    public void onUpdate(){
        boolean collide = false;
        if(Math.random() < 0.05){
            collide = spawnCheck(collide);
            addPedestrian(collide);
        }
        for(Pedestrian pedestrian : pedestrians) {
            pedestrian.update();
        }
        render();
    }

    //go through the list and make sure the pedestrians don't spawn on top of each other
    public boolean spawnCheck(boolean collide){
        blueRandY = Math.random() * canvas.getHeight();
        redRandY = Math.random() * canvas.getHeight();
        Pedestrian p1 = new Pedestrian(blueX, blueRandY, radius, 10000000, true);
        Pedestrian p2 = new Pedestrian(redX, redRandY, radius, 1000000, false);
        for(Pedestrian pedestrian : pedestrians) {
            if (p1.isColliding(pedestrian) || p2.isColliding(pedestrian)) {
                collide = true;
                break;
            }
        }
        return collide;
    }

    public void addPedestrian(boolean collide){
        if(collide == false){
            pedestrians.add(new Pedestrian(
                    blueX, blueRandY, radius, idCounter, true));
            idCounter++;
            pedestrians.add(new Pedestrian(
                    redX, redRandY, radius, idCounter, false));
            idCounter++;
        }
    }

    private void render(){
        g.clearRect(0, 0, 1200, 300);

        for(Pedestrian pedestrian : pedestrians){
            pedestrian.drawCenteredCircle(g, pedestrian);
        }
        g.strokeText(collisionCounter.toString(), 1140, 50);
        g.strokeText("Collisions: ", 1080, 50);
        g.strokeText(goalCounter.toString(), 1000, 50);
        g.strokeText("Goals: ", 960, 50);

        updateVelocity();
        collisionCheck();
        goalCheck();
        removePedestrian();
    }

    public void updateVelocity(){
        for(int i = 0; i < pedestrians.size(); i++) {
            for (int j = 0; j < pedestrians.size(); j++) {
                if (i != j) {
                    if(j == pedestrians.size() - 1){
                        pedestrians.get(i).scan(pedestrians.get(j), true);
                    }
                    else{
                        pedestrians.get(i).scan(pedestrians.get(j), false);
                    }
                }
            }
        }
    }

    public void collisionCheck(){
        for(int i = 0; i < pedestrians.size(); i++){
            for(int j = 0; j < pedestrians.size(); j++){
                if(i != j) { //this if runs two times for every collision :(
                    if (pedestrians.get(i).isColliding(pedestrians.get(j))) {
                        pedestrians.get(i).kill();
                        pedestrians.get(j).kill();
                        collisionCounter++;
                        System.out.println("hit!");
                    }
                }
            }
        }
    }

    public void goalCheck(){
        for(Pedestrian pedestrian : pedestrians){
            if(pedestrian.getXCenter() > 1150 ||
                    pedestrian.getXCenter() < 50){
                pedestrian.kill();
                goalCounter++;
            }
        }
    }

    public void removePedestrian(){
        Iterator<Pedestrian> iterator = this.pedestrians.iterator();
        while (iterator.hasNext()){
            if(!iterator.next().isAlive()){
                iterator.remove();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("Simulation");
        primaryStage.getScene().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                timer.start();
            }
            else if(event.getCode() == KeyCode.P){
                timer.stop();
            }
            else if(event.getCode() == KeyCode.Q){
                primaryStage.close();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
