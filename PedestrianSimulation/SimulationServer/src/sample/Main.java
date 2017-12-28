package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static List<Pedestrian> pedestrianList = Collections.synchronizedList(new ArrayList<Pedestrian>());
    public static Integer collisionCounter = 0;
    public static Integer goalCounter = 0;
    public static Integer canvasY = 400;
    public static Integer canvasX = 1200;
    public static Integer radius = 20;
    public static Integer timeSteps = 0;
    public static Integer spawnRate = 1500;
    public static Integer maxBluePedestrians = 10;
    public static Integer maxRedPedestrians = 10;
    public static boolean run = false;

    public static Integer getMaxBluePedestrians() {
        return maxBluePedestrians;
    }

    public static Integer getMaxRedPedestrians() {
        return maxRedPedestrians;
    }


    public static Integer getSpawnRate() {
        return spawnRate;
    }

    public static Integer getTimeSteps() {
        return timeSteps;
    }

    public static void setTimeSteps(Integer t) {
        timeSteps = t;
    }

    public static Integer getRadius() {
        return radius;
    }

    public static Integer getCanvasY() {
        return canvasY;
    }

    public static Integer getCanvasX() {
        return canvasX;
    }

    public static void toggleRun() {
        if(run == true)
            run = false;
        else
            run = true;
        //System.out.println("lol?");
    }
    public static boolean getRun() {
        return run;
    }

    public static List<Pedestrian> getPedestrianList() {
        return Main.pedestrianList;
    }

    public static Integer getCollisionCounter() {
        return collisionCounter;
    }
    public static void incCollisionCounter() {
        collisionCounter++;
    }
    public static Integer getGoalCounter() {
        return goalCounter;
    }
    public static void incGoalCounter() {
        goalCounter++;
    }

    public static void main(String[] args) {
        try {
            javafx.application.Application.launch(GUI.class);
        }
        finally {

        }
    }
}
