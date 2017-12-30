package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Pedestrian {

    public boolean alive = true;
    public Integer r;
    public Integer id;
    public Double xCenter, yCenter;

    public Pedestrian(Integer id, Double x, Double y) {
        this.r = Main.getRadius();
        this.id = id;
        this.xCenter = x;
        this.yCenter = y;
    }

    public double getXCenter() {
        return xCenter;
    }

    public void kill() {
        this.alive = false;
    }


    public void drawCenteredCircle(GraphicsContext g, Pedestrian pedestrian) {
        if (pedestrian.id == 1) {
            g.getCanvas().getGraphicsContext2D().setFill(Color.BLUE);
        } else {
            g.getCanvas().getGraphicsContext2D().setFill(Color.RED);
        }
        g.fillOval(xCenter, yCenter, r, r);
    }

    public boolean isColliding(Pedestrian other){
        return Math.sqrt(Math.pow(xCenter - other.xCenter, 2) +
                Math.pow(yCenter - other.yCenter, 2)) < r;
    }
}
