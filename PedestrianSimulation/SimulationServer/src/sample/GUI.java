package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import java.util.List;

public class GUI extends Application {

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
        canvas = new Canvas(Main.getCanvasX(), Main.getCanvasY());
        graphicsContext = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        render();
        return root;
    }

    public void onUpdate() {
        render();
    }

    private void render() {
        graphicsContext.clearRect(0, 0, Main.getCanvasX(), Main.getCanvasY());

        synchronized(this.pedestrians) {
            for (Pedestrian pedestrian : this.pedestrians) {
                pedestrian.drawCenteredCircle(graphicsContext, pedestrian);
            }
        }
        //red pedestrians
        graphicsContext.strokeText("Red tot pedestrians: ", 10, 40);
        graphicsContext.strokeText(Main.getMaxRedPedestrians().toString(), 120, 40);

        //blue pedestrians
        graphicsContext.strokeText("Blue tot pedestrians: ", 140, 40);
        graphicsContext.strokeText(Main.getMaxRedPedestrians().toString(), 250, 40);

        //Pedestrian size
        graphicsContext.strokeText("Pedestrian size: ", 270, 40);
        graphicsContext.strokeText(Main.getRadius().toString(), 355, 40);

        //Spawn rate
        graphicsContext.strokeText("Spawn rate: ", 375, 40);
        graphicsContext.strokeText(Main.getSpawnRate().toString(), 440, 40);

        //Width
        graphicsContext.strokeText("Width: ", 465, 40);
        graphicsContext.strokeText(Main.getCanvasX().toString(), 500, 40);

        //Height
        graphicsContext.strokeText("Height: ", 530, 40);
        graphicsContext.strokeText(Main.getCanvasY().toString(), 570, 40);

        //collision counter
        graphicsContext.strokeText("Collisions: ", 595.0, 40);
        graphicsContext.strokeText(Main.getCollisionCounter().toString(), 650.0, 40);

        //goal counter
        graphicsContext.strokeText("Succeeded:", 665.0, 40);
        graphicsContext.strokeText(Main.getGoalCounter() - Main.getCollisionCounter() + " / "
                + (Main.getMaxBluePedestrians()
                + Main.getMaxRedPedestrians()), 730.0, 40);

        //time steps
        graphicsContext.strokeText("Time-steps: ", 780.0, 40);
        graphicsContext.strokeText(Main.getTimeSteps().toString(), 850.0, 40);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.pedestrians = Main.getPedestrianList();

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("Pedestrian Simulation!!!");
        initialize();
        primaryStage.getScene().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                Main.toggleRun();
                timer.start();
            }
            else if(event.getCode() == KeyCode.P) {
                Main.toggleRun();
                timer.stop();
            }
            else if(event.getCode() == KeyCode.Q) {
                primaryStage.close();
            }
        });
        primaryStage.show();
    }
}
