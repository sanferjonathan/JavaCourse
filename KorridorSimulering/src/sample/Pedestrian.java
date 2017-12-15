package sample;

import javafx.scene.Node;

import javafx.geometry.Point2D;

public class Pedestrian {
    private Node view;
    private Point2D velocety = new Point2D(0, 0);
    private boolean alive = true;

    public Pedestrian(Node view){
        this.view = view;
    }

    public void update(){
        view.setTranslateX(view.getTranslateX() + velocety.getX());
        view.setTranslateY(view.getTranslateY() + velocety.getY());
    }

    public void setVelocety(Point2D velocety) {
        this.velocety = velocety;
    }

    public Point2D getVelocety() {
        return velocety;
    }

    public Node getView() {
        return view;
    }

    public boolean isAlive(){
        return alive;
    }

    public boolean isDead(){
        return !alive;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public double getRotate(){
        return view.getRotate();
    }

    public void rotateRight(){
        view.setRotate(view.getRotate() + 5);
        setVelocety(new Point2D(Math.cos(Math.toRadians(getRotate())),
                Math.sin(Math.toRadians(getRotate()))));
    }

    public void rotateLeft(){
        view.setRotate(view.getRotate() - 5);
        setVelocety(new Point2D(Math.cos(Math.toRadians(getRotate())),
                Math.sin(Math.toRadians(getRotate()))));
    }

    public boolean isColliding(Pedestrian other){
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }
}
