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

    static Server ServerTrad;
    private Integer canvasX = Main.getCanvasY();//1200
    private Integer canvasY = Main.getCanvasY();//300
    private final double blueX = 100, redX = 1100;
    private GraphicsContext g;
    private Pane root;
    private Canvas canvas;
    public List<Pedestrian> pedestrians = null;

    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            onUpdate();
        }
    };

    public static void initialize() {
        ServerTrad = new Server("ServerTrad"); //Statisk, speglas datan verkligen?
        ServerTrad.start();
    }


    private Parent createContent() {
        root = new Pane();
        canvas = new Canvas(Main.getCanvasX(), Main.getCanvasY());
        g = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        render();

        return root;
    }

    public void onUpdate() {
        render();
    }

    public void spawnCheck() {
        Double blueRandY = Math.random() * canvas.getHeight();
        Double redRandY = Math.random() * canvas.getHeight();
        Pedestrian p1 = new Pedestrian(1, blueX, blueRandY); //id x y SWITCHADE 0 1 ATM
        Pedestrian p2 = new Pedestrian(0, redX, redRandY);
        boolean pCollide = false;
        boolean p2Collide = false;

        synchronized(this.pedestrians) {
            for(Pedestrian p : this.pedestrians)
            {
                if (p1.isColliding(p))
                {
                    pCollide = true;
                }
                else if(p2.isColliding(p)) {
                    p2Collide = true;
                }
                if(pCollide || p2Collide)
                    break;
            }
        }

        if(!pCollide) {
            System.out.println("ADDED PEDESTRIAN, id x,y: " + p1.id + " " +  p1.xCenter + " " + p1.yCenter);
            synchronized(this.pedestrians) {
                Main.getPedestrianList().add(new Pedestrian(p1.id, p1.xCenter, p1.yCenter));
            }

        }
        else if(!p2Collide) {
            synchronized(this.pedestrians) {
                System.out.println("ADDED PEDESTRIAN, id:x,y" + p2.id + p2.xCenter + p2.yCenter);
                this.pedestrians.add(new Pedestrian(p2.id, p2.xCenter, p2.yCenter));
            }
        }
    }

    private void render() {
        g.clearRect(0, 0, Main.getCanvasX(), Main.getCanvasY());

        int inc = 0;
        synchronized(this.pedestrians) {
            for(Pedestrian pedestrian : this.pedestrians){
                pedestrian.drawCenteredCircle(g, pedestrian);
                inc++;
            }
        }
        System.out.println("Rendered peds:" + inc + " List size:" + this.pedestrians.size());

        g.strokeText(Main.getCollisionCounter().toString(), Main.getCanvasX()-60.0, 50);
        g.strokeText("Collision: ", Main.getCanvasX()-110.0, 50);
        g.strokeText(Main.getGoalCounter() - Main.getCollisionCounter() + " / "
                + (Main.getMaxBluePedestrians()
                + Main.getMaxRedPedestrians()), Main.getCanvasX()-240, 50);
        g.strokeText("Succeeded:", Main.getCanvasX()-310.0, 50);

        g.strokeText(Main.getTimeSteps().toString(), Main.getCanvasX()-390, 50);
        g.strokeText("Time-steps: ", Main.getCanvasX()-460.0, 50);

        g.strokeText("Width: " + Main.getCanvasX().toString() + " Height: "
                + Main.getCanvasY().toString() + " Spawnrate: " + Main.getSpawnRate().toString()
                + " Pedestrian size: " + Main.getRadius().toString() + " Red pedestrian limit: "
                + Main.getMaxRedPedestrians().toString() + " Blue pedestrian limit: "
                + Main.getMaxBluePedestrians().toString(), Main.getCanvasX()*0.05, 50);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.pedestrians = Main.getPedestrianList();

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("Simulation");
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
