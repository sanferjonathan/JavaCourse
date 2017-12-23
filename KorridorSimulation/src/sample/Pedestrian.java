package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class Pedestrian {

    private boolean alive = true, side;
    private final int r, id, close = 40, far = 100;
    private double xCenter, yCenter, velX = 0, velY = 0;
    private double upp = 0, down = 0, forward = 0;
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
    //gather all potential threats within 50 units in a list
    public void scan(Pedestrian other, boolean value){
        if(distanceCheck(other) < 100){
            this.threats.add(other);
        }
        if(value){
            distinguishThreat();
            this.threats.clear();
        }
        else{
            defaultVelocity();
        }
        System.out.println(this.threats.size());
    }

    public void defaultVelocity(){
        if(this.side){
            velX = 1.0;
            velY = 0;
        }
        else if(!this.side){
            velX = -1.0;
            velY = 0;
        }
    }

    public void distinguishThreat(){
        for(Pedestrian threat : threats){
            if(this.side != threat.side){
                enemyDodge(threat);
            }
            else if(this.side == threat.side && this.distanceCheck(threat) <= 40){
                friendlyPush(threat);
            }
            else if(this.side == threat.side && this.distanceCheck(threat) > 40){
                friendlyPull(threat);
            }
        }
        calculateMove();
    }

    public void enemyDodge(Pedestrian threat){
        if(threat.yCenter <= this.yCenter
                && this.yCenter <= threat.yCenter + 40){
            upp += 4.0/distanceCheck(threat);
            down += 2.5/distanceCheck(threat);
        }
        else if(threat.yCenter >= this.yCenter
                && this.yCenter >= threat.yCenter - 40){
            down += 4.0/distanceCheck(threat);
            upp += 2.5/distanceCheck(threat);
        }
        else {
            forward += 0.3/distanceCheck(threat);
        }
    }

    public void friendlyPush(Pedestrian threat){
        if(southWest(threat, close)){
            forward += 0.3/distanceCheck(threat);
            upp += 1.0/distanceCheck(threat);
        }
        else if(northEast(threat, close)){
            forward += 0.3/distanceCheck(threat);
            down += 1.0/distanceCheck(threat);
        }
        else if(southEast(threat, close)){
            forward += 0.3/distanceCheck(threat);
            upp += 1.0/distanceCheck(threat);
        }
        else if(northWest(threat, close)){
            forward += 0.3/distanceCheck(threat);
            down += 1.0/distanceCheck(threat);
        }
        else {
            forward += 0.3/distanceCheck(threat);
        }
    }

    public void friendlyPull(Pedestrian threat){
        if(southWest(threat, far)){
            forward += 0.3/distanceCheck(threat);
            down += 0.4/distanceCheck(threat);
        }
        else if(southEast(threat, far)){
            forward += 0.3/distanceCheck(threat);
            down += 0.4/distanceCheck(threat);
        }
        if(northEast(threat, far)){
            forward += 0.3/distanceCheck(threat);
            upp += 0.4/distanceCheck(threat);
        }
        else if(northWest(threat, far)){
            forward += 0.3/distanceCheck(threat);
            upp += 0.4/distanceCheck(threat);
        }
        else {
            forward += 0.3 / distanceCheck(threat);
        }
    }

    public void calculateMove(){
        if(forward > upp && forward > down){
            if(this.side){
                velX = 1.0;
            }
            else{
                velX = -1.0;
            }
            velY = 0;
        }
        else if(upp > forward &&  upp > down){
            if(this.side){
                velX = 0.5;
            }
            else{
                velX = -0.5;
            }
            velY = 0.5;
        }
        else if(down > forward && down > upp){
            if(this.side){
                velX = 0.5;
            }
            else{
                velX = -0.5;
            }
            velY = -0.5;
        }
        upp = 0;
        down = 0;
        forward = 0;
    }

    public boolean southWest(Pedestrian threat, int range){
        return threat.yCenter <= this.yCenter
                && this.yCenter <= threat.yCenter + range
                && threat.xCenter <= this.xCenter
                && this.xCenter <= threat.xCenter + range;
    }

    public boolean northEast(Pedestrian threat, int range){
        return threat.yCenter >= this.yCenter
                && this.yCenter >= threat.yCenter - range
                && threat.xCenter >= this.xCenter
                && this.xCenter >= threat.xCenter - range;
    }

    public boolean southEast(Pedestrian threat, int range){
        return threat.yCenter <= this.yCenter
                && this.yCenter <= threat.yCenter + range
                && threat.xCenter >= this.xCenter
                && this.xCenter >= threat.xCenter - range;
    }

    public boolean northWest(Pedestrian threat, int range){
        return threat.yCenter >= this.yCenter
                && this.yCenter >= threat.yCenter - range
                && threat.xCenter <= this.xCenter
                && this.xCenter <= threat.xCenter + range;
    }

    public double distanceCheck(Pedestrian threat){
        return Math.sqrt(Math.pow(this.xCenter - threat.xCenter, 2) +
                Math.pow(this.yCenter - threat.yCenter, 2));
    }

    public boolean isColliding(Pedestrian other){
        return bBox().isColliding(other.bBox());
    }
}