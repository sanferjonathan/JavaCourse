package sample;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PedestrianTest {
    Pedestrian pedestrian1;
    Pedestrian pedestrian2;
    Pedestrian pedestrian3;
    Pedestrian pedestrian4;
    Pedestrian pedestrian5;
    Pedestrian pedestrian6;
    Pedestrian pedestrian7;
    Pedestrian pedestrian8;
    Pedestrian pedestrian9;

    @Before
    public void setUp() throws Exception {
        pedestrian1 = new Pedestrian(1, 20.0, 50.0);
        pedestrian2 = new Pedestrian(0, 30.0, 50.0);
        pedestrian3 = new Pedestrian(1, 20.0, 80.0);
        pedestrian4 = new Pedestrian(0, 20.0, 50.0);
        pedestrian5 = new Pedestrian(1, 10.0, 40.0);
        pedestrian6 = new Pedestrian(0, 320.0, 50.0);
        pedestrian7 = new Pedestrian(0, 200.0, 60.0);
        pedestrian8 = new Pedestrian(0, 90.0, 80.0);
        pedestrian9 = new Pedestrian(1, 30.0, 60.0);
    }

    @Test
    public void scan() {
        assertEquals(0.0, pedestrian1.getVelX(), 0.001);
        pedestrian1.scan(pedestrian2, false);
        assertEquals(4.0, pedestrian1.getVelX(), 0.001);
        pedestrian1.scan(pedestrian3, false);
        pedestrian1.scan(pedestrian6, false);
        assertEquals(2, pedestrian1.getNeighbors().size());
        assertEquals(4.0, pedestrian1.getVelX(), 0.001);
        pedestrian1.scan(pedestrian4, true);
        assertEquals(0, pedestrian1.getNeighbors().size());
    }

    @Test
    public void distanceCheck() {
        double dist = pedestrian1.distanceCheck(pedestrian2);
        assertEquals(10.0, dist, 0.001);
        dist = pedestrian4.distanceCheck(pedestrian3);
        assertEquals(30.0, dist, 0.001);
    }

    @Test
    public void defaultVelocity() {
        pedestrian1.defaultVelocity();
        assertEquals(4.0, pedestrian1.getVelX(), 0.001);
        assertEquals(0.0, pedestrian1.getVelY(), 0.001);
        pedestrian2.defaultVelocity();
        assertEquals(-4.0, pedestrian2.getVelX(), 0.001);
        assertEquals(0.0, pedestrian1.getVelY(), 0.001);
    }

    @Test
    public void enemyDodge() {
        pedestrian1.enemyDodge(pedestrian3);
        pedestrian1.calculateMove();
        assertEquals(2.0, pedestrian1.getVelX(), 0.001);
        assertEquals(-2.0, pedestrian1.getVelY(), 0.001);

        pedestrian1.enemyDodge(pedestrian5);
        pedestrian1.calculateMove();
        assertEquals(2.0, pedestrian1.getVelX(), 0.001);
        assertEquals(2.0, pedestrian1.getVelY(), 0.001);
    }

    @Test
    public void friendlyPush() {
        pedestrian1.friendlyPush(pedestrian3);
        pedestrian1.calculateMove();
        assertEquals(4.0, pedestrian1.getVelX(), 0.001);
        assertEquals(0.0, pedestrian1.getVelY(), 0.001);

        pedestrian1.friendlyPush(pedestrian5);
        pedestrian1.calculateMove();
        assertEquals(2.0, pedestrian1.getVelX(), 0.001);
        assertEquals(2.0, pedestrian1.getVelY(), 0.001);

        pedestrian1.friendlyPush(pedestrian7);
        pedestrian1.calculateMove();
        assertEquals(4.0, pedestrian1.getVelX(), 0.001);
        assertEquals(0.0, pedestrian1.getVelY(), 0.001);

        pedestrian1.friendlyPush(pedestrian9);
        pedestrian1.calculateMove();
        assertEquals(2.0, pedestrian1.getVelX(), 0.001);
        assertEquals(-2.0, pedestrian1.getVelY(), 0.001);
    }

    @Test
    public void friendlyPull() {
        pedestrian1.friendlyPull(pedestrian8);
        pedestrian1.calculateMove();
        assertEquals(2.0, pedestrian1.getVelX(), 0.001);
        assertEquals(2.0, pedestrian1.getVelY(), 0.001);

        pedestrian1.friendlyPull(pedestrian5);
        pedestrian1.calculateMove();
        assertEquals(2.0, pedestrian1.getVelX(), 0.001);
        assertEquals(-2.0, pedestrian1.getVelY(), 0.001);

        pedestrian1.friendlyPull(pedestrian7);
        pedestrian1.calculateMove();
        assertEquals(2.0, pedestrian1.getVelX(), 0.001);
        assertEquals(2.0, pedestrian1.getVelY(), 0.001);

        pedestrian1.friendlyPush(pedestrian6);
        pedestrian1.calculateMove();
        assertEquals(4.0, pedestrian1.getVelX(), 0.001);
        assertEquals(0.0, pedestrian1.getVelY(), 0.001);
    }

    @Test
    public void update() {
        pedestrian1.defaultVelocity();
        pedestrian1.update();
        assertEquals(24.0, pedestrian1.getXCenter(), 0.001);

        pedestrian1.friendlyPush(pedestrian5);
        pedestrian1.calculateMove();
        pedestrian1.update();
        assertEquals(26.0, pedestrian1.getXCenter(), 0.001);
        assertEquals(52.0, pedestrian1.getYCenter(), 0.001);
    }
}