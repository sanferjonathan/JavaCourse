package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Pedestrian {

    private boolean alive = true;
    private Integer r;
    private Integer id;
    private Double xCenter, yCenter;

    public Pedestrian(Integer id, Double x, Double y) {
        this.r = Main.Settings.getRadius();
        this.id = id;
        this.xCenter = x;
        this.yCenter = y;
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

    public boolean isAlive() {
        return alive;
    }

    public Integer getId() {
        return id;
    }

    public double getXCenter() {
        return xCenter;
    }

    public Double getYCenter() {
        return yCenter;
    }

    public String getXCenterToString() {
        return xCenter.toString();
    }

    public String getYCenterToString() {
        return yCenter.toString();
    }

    public void kill() {
        this.alive = false;
    }
}
