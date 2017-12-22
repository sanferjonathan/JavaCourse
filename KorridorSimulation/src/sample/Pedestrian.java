package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class Pedestrian {

    private boolean alive = true, side;
    private int r, id;
    private double xCenter, yCenter, velX = 0, velY = 0;
    private List<Pedestrian> threats = new ArrayList<>();


    public Pedestrian(double x, double y, int r, int id, boolean side){
        this.id = id;
        this.xCenter = x-(r/2);
        this.yCenter = y-(r/2);
        this.r = r;
        this.side = side;
    }

    public void update(){
        this.xCenter += this.velX;
        this.yCenter += this.velY;
    }

    public double getXCenter(){
        return xCenter;
    }

    public boolean getSide(){
        return side;
    }

    public boolean isAlive(){
        return alive;
    }

    public void kill(){
        alive = false;
    }

    public void drawCenteredCircle(GraphicsContext g, Pedestrian pedestrian) {
        if(pedestrian.getSide()){
            g.getCanvas().getGraphicsContext2D().setFill(Color.BLUE);
        }
        else{
            g.getCanvas().getGraphicsContext2D().setFill(Color.RED);
        }
        g.fillOval(xCenter, yCenter,r,r);
    }

    public BBox bBox(){
        return new BBox(xCenter, yCenter, r, id);
    }
    //gather all potential threats within 100 units in a list
    public void scan(Pedestrian other, boolean value){
        if(((other.yCenter <= this.yCenter
                && this.yCenter <= other.yCenter + 100)
                || (other.yCenter >= this.yCenter
                && this.yCenter >= other.yCenter - 100))
                && ((other.xCenter <= this.xCenter
                && this.xCenter <= other.xCenter + 100)
                || (other.xCenter >= this.xCenter
                && this.xCenter >= other.xCenter - 100))){
            this.threats.add(other);
        }
        if(value){
            distinguishThreat();
        }
        else {
            defaultVelocityBlue();
            defaultVelocityRed();
        }
    }

    public void defaultVelocityBlue(){
        if(this.side){
            velX = 1.0;
            velY = 0;
        }
    }

    public void defaultVelocityRed(){
        if(!this.side){
            velX = -1.0;
            velY = 0;
        }
    }

    public void distinguishThreat(){
        for(Pedestrian threat : threats){
            //blue agent(this) meets red agent(threat)
            if(this.side != threat.side && this.side == true){
                blueVsRed(threat);
            }
            //red agent(this) meets blue agent(threat)
            else if(this.side != threat.side && this.side == false){
                redVsBlue(threat);
            }
            //blue agent this moves alongside blue agent (threat)
            //else if(this.side == threat.side && this.side == true){
                //dodge(threat);
            //}
            //red agent this moves alongside red agent (threat)
            //else{
                //dodge(threat);
            //}
        }
    }

    public void blueVsRed(Pedestrian threat){
        if(threat.yCenter <= this.yCenter
                && this.yCenter <= threat.yCenter + 20){
            velX = 0.5;
            velY = 0.5;
        }
        else if(threat.yCenter >= this.yCenter
                && this.yCenter >= threat.yCenter - 20){
            velX = 0.5;
            velY = -0.5;
        }
        else {
            velX = 1.0;
            velY = 0;
        }
    }

    public void redVsBlue(Pedestrian threat){
        if(threat.yCenter <= this.yCenter
                && this.yCenter <= threat.yCenter + 20){
            velX = -0.5;
            velY = 0.5;
        }
        else if(threat.yCenter >= this.yCenter
                && this.yCenter >= threat.yCenter - 20){
            velX = -0.5;
            velY = -0.5;
        }
        else {
            velX = -1.0;
            velY = 0;
        }
    }

    public boolean isColliding(Pedestrian other){
        return bBox().isColliding(other.bBox());
    }
}
