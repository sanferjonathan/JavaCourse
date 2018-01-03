package sample;

import java.util.ArrayList;
import java.util.List;

public class Pedestrian {

    private Integer id;
    private Integer close = 40, far = 300;
    private Double xCenter, yCenter, velX = 0.0, velY = 0.0;
    private List<Pedestrian> neighbors = new ArrayList<>();
    private double upp = 0, down = 0, forward = 0;


    public Pedestrian(Integer id, Double x, Double y) {
        this.id = id;
        this.xCenter = x;//-(r/2);
        this.yCenter = y;//-(r/2);
    }


    public void scan(Pedestrian neighbor, boolean value) {
        defaultVelocity();
        if(distanceCheck(neighbor) < 300) {
            this.neighbors.add(neighbor);
        }
        if(value) {
            sortNeighbors();
            this.neighbors.clear();
        }
    }

    public void defaultVelocity() {
        if(this.id == 1) {
            velX = 1.0;
            velY = 0.0;
        }
        else if(this.id == 0) {
            velX = -1.0;
            velY = 0.0;
        }
    }

    public double distanceCheck(Pedestrian neighbor) {
        return Math.sqrt(Math.pow(this.xCenter - neighbor.xCenter, 2) +
                Math.pow(this.yCenter - neighbor.yCenter, 2));
    }

    public void sortNeighbors() {
        for(Pedestrian neighbor : neighbors) {
            if(this.id != neighbor.id) {
                enemyDodge(neighbor);
            }
            else if(this.id == neighbor.id
                    && this.distanceCheck(neighbor) <= 40) {
                friendlyPush(neighbor);
            }
            else if(this.id == neighbor.id
                    && this.distanceCheck(neighbor) > 100) {
                friendlyPull(neighbor);
            }
        }
        calculateMove();
    }

    public void enemyDodge(Pedestrian neighbor) {
        if(neighbor.yCenter <= this.yCenter
                && this.yCenter <= neighbor.yCenter + 40) {
            upp += 10.0/distanceCheck(neighbor);
            down += 5.0/distanceCheck(neighbor);
        }
        else if(neighbor.yCenter >= this.yCenter
                && this.yCenter >= neighbor.yCenter - 40) {
            down += 10.0/distanceCheck(neighbor);
            upp += 5.0/distanceCheck(neighbor);
        }
        else {
            forward += 0.1/distanceCheck(neighbor);
        }
    }

    public void friendlyPush(Pedestrian neighbor) {
        if(southWest(neighbor, 0, close)) {
            forward += 0.1/distanceCheck(neighbor);
            upp += 5.0/distanceCheck(neighbor);
        }
        else if(northEast(neighbor, 0, close)) {
            forward += 0.1/distanceCheck(neighbor);
            down += 5.0/distanceCheck(neighbor);
        }
        else if(southEast(neighbor, 0, close)) {
            forward += 0.1/distanceCheck(neighbor);
            upp += 5.0/distanceCheck(neighbor);
        }
        else if(northWest(neighbor, 0, close)) {
            forward += 0.1/distanceCheck(neighbor);
            down += 5.0/distanceCheck(neighbor);
        }
        else {
            forward += 0.1/distanceCheck(neighbor);
        }
    }

    public void friendlyPull(Pedestrian neighbor) {
        if(southWest(neighbor, 100, far)) {
            forward += 0.1/distanceCheck(neighbor);
            down += 0.4/distanceCheck(neighbor);
        }
        else if(southEast(neighbor, 100, far)) {
            forward += 0.1/distanceCheck(neighbor);
            down += 0.4/distanceCheck(neighbor);
        }
        if(northEast(neighbor, 100, far)) {
            forward += 0.1/distanceCheck(neighbor);
            upp += 0.4/distanceCheck(neighbor);
        }
        else if(northWest(neighbor, 100, far)) {
            forward += 0.1/distanceCheck(neighbor);
            upp += 0.4/distanceCheck(neighbor);
        }
        else {
            forward += 0.1 / distanceCheck(neighbor);
        }
    }

    public void calculateMove() {
        if(forward > upp && forward > down) {
            if(this.id == 1) {
                velX = 1.0;
            }
            else{
                velX = -1.0;
            }
            velY = 0.0;
        }
        else if(upp > forward &&  upp > down) {
            if(this.id == 1) {
                velX = 0.5;
            }
            else{
                velX = -0.5;
            }
            velY = 0.5;
        }
        else if(down > forward && down > upp) {
            if(this.id == 1) {
                velX = 0.5;
            }
            else {
                velX = -0.5;
            }
            velY = -0.5;
        }
        upp = 0;
        down = 0;
        forward = 0;
    }

    public boolean southWest(Pedestrian neighbor, int range1, int range2) {
        return neighbor.yCenter < this.yCenter
                && this.yCenter + range1 < neighbor.yCenter + range2
                && neighbor.xCenter < this.xCenter
                && this.xCenter + range1 < neighbor.xCenter + range2;
    }

    public boolean northEast(Pedestrian neighbor, int range1, int range2) {
        return neighbor.yCenter > this.yCenter
                && this.yCenter - range1 > neighbor.yCenter - range2
                && neighbor.xCenter > this.xCenter
                && this.xCenter - range1 > neighbor.xCenter - range2;
    }

    public boolean southEast(Pedestrian neighbor, int range1, int range2) {
        return neighbor.yCenter < this.yCenter
                && this.yCenter + range1 < neighbor.yCenter + range2
                && neighbor.xCenter > this.xCenter
                && this.xCenter - range1 > neighbor.xCenter - range2;
    }

    public boolean northWest(Pedestrian neighbor, int range1, int range2) {
        return neighbor.yCenter > this.yCenter
                && this.yCenter - range1 > neighbor.yCenter - range2
                && neighbor.xCenter < this.xCenter
                && this.xCenter + range1 < neighbor.xCenter + range2;
    }

    public void update() {
        this.xCenter += this.velX;
        this.yCenter += this.velY;
    }

    public Integer getId() {
        return id;
    }

    public Double getXCenter() {
        return xCenter;
    }

    public Double getYCenter() {
        return yCenter;
    }

    public Double getVelX() {
        return velX;
    }

    public Double getVelY() {
        return velY;
    }

    public List<Pedestrian> getNeighbors() {
        return neighbors;
    }
}
