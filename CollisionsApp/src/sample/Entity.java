package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;

public class Entity {

    private int x, y, w, h;

    public Entity(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(GraphicsContext g){
        g.strokeRect(x, y, w, h);
    }

    public BBox bBox(){
        return new BBox(x, x + w, y, y + h);
    }

    public boolean isColliding(Entity other){
        return bBox().isColliding(other.bBox());
    }

    public void incrementX(){
        this.x += 5;
    }

    public void decrementX(){
        this.x -= 5;
    }

    public void incrementY(){
        this.y += 5;
    }

    public void decrementY(){
        this.y -= 5;
    }
}
