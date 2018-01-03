package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class Main extends Application{

    private GraphicsContext graphicsContext;
    private Pane root;
    private Canvas canvas;
    private List<Pedestrian> pedestrians = null;

    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            onUpdate();
        }
    };

    public static void initialize() {
        Server serverThread;
        serverThread = new Server("serverThread");
        serverThread.start();
    }

    private Parent createContent() {
        root = new Pane();
        canvas = new Canvas(Settings.getCanvasX(), Settings.getCanvasY());
        graphicsContext = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        render();
        return root;
    }

    public void onUpdate() {
        render();
    }

    private void render() {
        graphicsContext.clearRect(0, 0, Settings.getCanvasX(), Settings.getCanvasY());

        synchronized(this.pedestrians) {
            for (Pedestrian pedestrian : this.pedestrians) {
                pedestrian.drawCenteredCircle(graphicsContext, pedestrian);
            }
        }
        //red pedestrians
        graphicsContext.strokeText("Red tot pedestrians: ", 10, 40);
        graphicsContext.strokeText(Settings.getMaxRedPedestrians().toString(), 120, 40);

        //blue pedestrians
        graphicsContext.strokeText("Blue tot pedestrians: ", 140, 40);
        graphicsContext.strokeText(Settings.getMaxRedPedestrians().toString(), 250, 40);

        //Pedestrian size
        graphicsContext.strokeText("Pedestrian size: ", 270, 40);
        graphicsContext.strokeText(Settings.getRadius().toString(), 355, 40);

        //Spawn rate
        graphicsContext.strokeText("Spawn rate: ", 375, 40);
        graphicsContext.strokeText(Settings.getSpawnRate().toString(), 440, 40);

        //Width
        graphicsContext.strokeText("Width: ", 465, 40);
        graphicsContext.strokeText(Settings.getCanvasX().toString(), 500, 40);

        //Height
        graphicsContext.strokeText("Height: ", 530, 40);
        graphicsContext.strokeText(Settings.getCanvasY().toString(), 570, 40);

        //collision counter
        graphicsContext.strokeText("Collisions: ", 595.0, 40);
        graphicsContext.strokeText(Settings.getCollisionCounter().toString(), 650.0, 40);

        //goal counter
        graphicsContext.strokeText("Succeeded:", 665.0, 40);
        graphicsContext.strokeText(Settings.getGoalCounter() - Settings.getCollisionCounter() + " / "
                + (Settings.getMaxBluePedestrians()
                + Settings.getMaxRedPedestrians()), 730.0, 40);

        //time steps
        graphicsContext.strokeText("Time-steps: ", 780.0, 40);
        graphicsContext.strokeText(Settings.getTimeSteps().toString(), 850.0, 40);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.pedestrians = Settings.getPedestrianList();

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("Pedestrian Simulation!!!");
        initialize();
        primaryStage.getScene().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                Settings.toggleRun();
                timer.start();
            }
            else if(event.getCode() == KeyCode.P) {
                Settings.toggleRun();
                timer.stop();
            }
            else if(event.getCode() == KeyCode.Q) {
                primaryStage.close();
            }
        });
        primaryStage.show();
    }

    static class Settings {

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
            return pedestrianList;
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
    }


    public static void main(String[] args) {
        try {
            javafx.application.Application.launch(Main.class);
        }
        finally {

        }
    }
}
