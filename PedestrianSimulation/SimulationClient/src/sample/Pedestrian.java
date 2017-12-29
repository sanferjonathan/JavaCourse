package sample;

import java.util.ArrayList;
import java.util.List;

public class Pedestrian {

    public Integer r = 20;
    public Integer id;
    public Integer close = 40, far = 200;
    public Double xCenter, yCenter, velX = 0.0, velY = 0.0;
    public List<Pedestrian> threats = new ArrayList<>();
    private double upp = 0, down = 0, forward = 0;


    public Pedestrian(Integer id, Double x, Double y) {
        this.id = id;
        this.xCenter = x;//-(r/2);
        this.yCenter = y;//-(r/2);
    }


    public void update() {
        this.xCenter += this.velX;
        this.yCenter += this.velY;
    }

    public BBox bBox() {
        return new BBox(xCenter, yCenter, r, id);
    }

    public void scan(Pedestrian other, boolean value) {
        if(distanceCheck(other) < 100) {
            this.threats.add(other);
        }
        if(value) {
            distinguishThreat();
            this.threats.clear();
        }
        else {
            defaultVelocity();
        }
    }


    public void defaultVelocity() {
        if(this.id == 1) {
            System.out.println("Move forward");
            velX = 4.0;
            velY = 0.0;
        }
        else if(this.id == 0) {
            System.out.println("Move forward");
            velX = -4.0;
            velY = 0.0;
        }
    }

    public void distinguishThreat() {
        for(Pedestrian threat : threats) {
            if(this.id != threat.id) {
                enemyDodge(threat);
            }
            else if(this.id == threat.id
                    && this.distanceCheck(threat) <= 40) {
                friendlyPush(threat);
            }
            else if(this.id == threat.id
                    && this.distanceCheck(threat) > 40) {
                friendlyPull(threat);
            }
        }
        calculateMove();
    }

    public void enemyDodge(Pedestrian threat) {
        if(threat.yCenter <= this.yCenter
                && this.yCenter <= threat.yCenter + 40) {
            upp += 10.0/distanceCheck(threat);
            down += 5.0/distanceCheck(threat);
        }
        else if(threat.yCenter >= this.yCenter
                && this.yCenter >= threat.yCenter - 40) {
            down += 10.0/distanceCheck(threat);
            upp += 5.0/distanceCheck(threat);
        }
        else {
            forward += 0.1/distanceCheck(threat);
        }
    }

    public void friendlyPush(Pedestrian threat) {
        if(southWest(threat, 0, close)) {
            forward += 0.1/distanceCheck(threat);
            upp += 5.0/distanceCheck(threat);
        }
        else if(northEast(threat, 0, close)) {
            forward += 0.1/distanceCheck(threat);
            down += 5.0/distanceCheck(threat);
        }
        else if(southEast(threat, 0, close)) {
            forward += 0.1/distanceCheck(threat);
            upp += 5.0/distanceCheck(threat);
        }
        else if(northWest(threat, 0, close)) {
            forward += 0.1/distanceCheck(threat);
            down += 5.0/distanceCheck(threat);
        }
        else {
            forward += 0.1/distanceCheck(threat);
        }
    }

    public void friendlyPull(Pedestrian threat) {
        if(southWest(threat, 100, far)) {
            forward += 0.1/distanceCheck(threat);
            down += 0.4/distanceCheck(threat);
        }
        else if(southEast(threat, 100, far)) {
            forward += 0.1/distanceCheck(threat);
            down += 0.4/distanceCheck(threat);
        }
        if(northEast(threat, 100, far)) {
            forward += 0.1/distanceCheck(threat);
            upp += 0.4/distanceCheck(threat);
        }
        else if(northWest(threat, 100, far)) {
            forward += 0.1/distanceCheck(threat);
            upp += 0.4/distanceCheck(threat);
        }
        else {
            forward += 0.1 / distanceCheck(threat);
        }
    }

    public void calculateMove() {
        if(forward > upp && forward > down) {
            if(this.id == 1) {
                velX = 4.0;
            }
            else{
                velX = -4.0;
            }
            velY = 0.0;
        }
        else if(upp > forward &&  upp > down) {
            if(this.id == 1) {
                velX = 2.0;
            }
            else{
                velX = -2.0;
            }
            velY = 2.0;
        }
        else if(down > forward && down > upp) {
            if(this.id == 1) {
                velX = 2.0;
            }
            else {
                velX = -2.0;
            }
            velY = -2.0;
        }
        upp = 0;
        down = 0;
        forward = 0;
    }

    public boolean southWest(Pedestrian threat, int range1, int range2) {
        return threat.yCenter <= this.yCenter
                && this.yCenter + range1 <= threat.yCenter + range2
                && threat.xCenter <= this.xCenter
                && this.xCenter + range1 <= threat.xCenter + range2;
    }

    public boolean northEast(Pedestrian threat, int range1, int range2) {
        return threat.yCenter >= this.yCenter
                && this.yCenter - range1 >= threat.yCenter - range2
                && threat.xCenter >= this.xCenter
                && this.xCenter - range1 >= threat.xCenter - range2;
    }

    public boolean southEast(Pedestrian threat, int range1, int range2) {
        return threat.yCenter <= this.yCenter
                && this.yCenter + range1 <= threat.yCenter + range2
                && threat.xCenter >= this.xCenter
                && this.xCenter - range1 >= threat.xCenter - range2;
    }

    public boolean northWest(Pedestrian threat, int range1, int range2) {
        return threat.yCenter >= this.yCenter
                && this.yCenter - range1 >= threat.yCenter - range2
                && threat.xCenter <= this.xCenter
                && this.xCenter + range1 <= threat.xCenter + range2;
    }

    public double distanceCheck(Pedestrian threat) {
        return Math.sqrt(Math.pow(this.xCenter - threat.xCenter, 2) +
                Math.pow(this.yCenter - threat.yCenter, 2));
    }
}
