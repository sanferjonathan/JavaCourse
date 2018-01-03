package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    private static Integer collisionCounter = 0;
    private static Integer goalCounter = 0;
    private static Integer canvasY = 300;
    private static Integer canvasX = 1200;
    private static Integer radius = 20;
    private static Integer timeSteps = 0;
    private static Integer spawnRate = 400;
    private static Integer maxBluePedestrians = 50;
    private static Integer maxRedPedestrians = 50;
    private static boolean run = false;

    private static List<Pedestrian> pedestrianList = Collections
            .synchronizedList(new ArrayList<Pedestrian>());

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
        if(run)
            run = false;
        else
            run = true;
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
