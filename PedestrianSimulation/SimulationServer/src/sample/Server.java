package sample;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;

public class Server implements Runnable {

    private ServerSocket server;
    private Socket connection;
    private Integer numberOfClients = 0;
    private Thread thread;
    private String threadName;
    private Integer timeStamp = 0;
    private long pastTime = 0L;
    private Integer spawnedBluePedestrians = 1;
    private Integer spawnedRedPedestrians = 1;

    private ArrayList<ServerClient> clientList = new ArrayList<>();
    private List<Pedestrian> pedestrianList;

    Server(String name) {
        this.pedestrianList = Main.getPedestrianList();
        threadName = name;
    }

    public void start () {
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public void run() {
        try {
            Listen();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Listen() throws IOException, InterruptedException {
        this.server = new ServerSocket(420,5);
        this.server.setReuseAddress(true);

        this.waitingForConnections();
        this.spawnCheck();
        while(true) {
            System.out.println(""); //needed lol?
            if(Main.getRun()) {
                this.Broadcast();
                this.waitingForAnswer();
                this.updateData();
                this.goalCheck();
                this.collisionCheck();
                this.removePedestrian();
                this.onUpdate();
                Main.setTimeSteps(++this.timeStamp);
                System.out.println(""); //needed lol?
            }
        }
    }

    public void waitingForConnections() throws IOException {
        while(this.numberOfClients < 2) {
            this.connection = server.accept();
            this.numberOfClients++;
            ServerClient client = new ServerClient(connection, this.numberOfClients - 1);
            new Thread(client).start();
            this.clientList.add(client);
        }
    }
    //sends a string with all pedestrians to all clients
    private void Broadcast() throws IOException {
        String pedestrians = generateSerializedPedestrianString();
        for(ServerClient client : this.clientList) {
            client.sendToClient(pedestrians);
        }
    }

    private String generateSerializedPedestrianString() throws IOException {
        String pedestrianList = "";
        while(pedestrianList == "" && !this.pedestrianList.isEmpty()) {
            synchronized(this.pedestrianList) {
                Iterator<Pedestrian> iterator = this.pedestrianList.iterator();
                while (iterator.hasNext()) {
                    Pedestrian pedestrian = iterator.next();
                    pedestrianList += pedestrian.getId().toString() +
                            ";" + pedestrian.getXCenterToString() +
                            ";" + pedestrian.getYCenterToString() + "/";
                }
            }
        }
        return pedestrianList;
    }

    public void waitingForAnswer() throws IOException, InterruptedException {
        Integer numberOfAnswers = 0;
        while(numberOfAnswers < this.numberOfClients) {
            numberOfAnswers = 0;
            for(ServerClient client : this.clientList) {
                if(!client.getPedestrianList().isEmpty()) {
                    numberOfAnswers++;
                }
            }
        }
    }
    //clear the global list and add pedestrians with new coordinates from ServerClients list
    private void updateData() throws IOException, InterruptedException {
        Thread.sleep(10);
        this.pedestrianList.clear();
        for(ServerClient client : this.clientList) {
            synchronized(client.getPedestrianList()) {
                if(!client.getPedestrianList().isEmpty()) {
                    for(Pedestrian pedestrian : client.getPedestrianList()) {
                        Main.getPedestrianList().add(
                                new Pedestrian(pedestrian.getId(),
                                pedestrian.getXCenter(), pedestrian.getYCenter()));
                    }
                }
            }
        }
    }

    public void goalCheck() {
        synchronized(this.pedestrianList) {
            for(Pedestrian pedestrian : this.pedestrianList) {
                if(pedestrian.getXCenter() > Main.getCanvasX()-50.0
                        || pedestrian.getXCenter() < 50) {
                    pedestrian.kill();
                    if(Main.getGoalCounter() < (Main.getMaxBluePedestrians()
                            + Main.getMaxRedPedestrians()))
                        Main.incGoalCounter();
                }
            }
        }
    }

    public void collisionCheck() {
        synchronized(this.pedestrianList) {
            for(int i = 0; i < this.pedestrianList.size(); i++) {
                for(int j = 0; j < this.pedestrianList.size(); j++) {
                    if(this.pedestrianList.get(j).isAlive() && i != j) {
                        if (this.pedestrianList.get(i)
                                .isColliding(this.pedestrianList.get(j))) {
                            this.pedestrianList.get(i).kill();
                            this.pedestrianList.get(j).kill();
                            Main.incCollisionCounter();
                        }
                    }
                }
            }
        }
    }

    public void removePedestrian() {
        synchronized(this.pedestrianList) {
            Iterator<Pedestrian> iterator = this.pedestrianList.iterator();
            while (iterator.hasNext()){
                if(!iterator.next().isAlive()) {
                    iterator.remove();
                }
            }
        }
    }

    public void onUpdate() {
        long millis = System.currentTimeMillis();
        if(millis >= (pastTime + Main.getSpawnRate())) {
            this.spawnCheck();
            pastTime = millis + Main.getSpawnRate();
        }
    }

    public void spawnCheck() {
        Double blueRandY = Math.random() * Main.getCanvasY();
        Double redRandY = Math.random() * Main.getCanvasY();
        Pedestrian p1 = new Pedestrian(1, 100.0, blueRandY);
        Pedestrian p2 = new Pedestrian(0, 1100.0, redRandY);

        boolean pCollide = false;
        boolean p2Collide = false;
        synchronized(this.pedestrianList) {
            for(Pedestrian p : this.pedestrianList) {
                if (p1.isColliding(p)) {
                    pCollide = true;
                }
                if(p2.isColliding(p)) {
                    p2Collide = true;
                }
                if(pCollide || p2Collide) {
                    break;
                }
            }
        }

        if(!pCollide) {
            if(this.spawnedBluePedestrians <= Main.getMaxBluePedestrians()
                    && this.spawnedBluePedestrians <= this.spawnedRedPedestrians) {
                synchronized(this.pedestrianList) {
                    this.pedestrianList.add(new Pedestrian(p1.getId(), p1.getXCenter(), p1.getYCenter()));
                    this.spawnedBluePedestrians++;
                }
            }
        }

        if(!p2Collide) {
            if(this.spawnedRedPedestrians <= Main.getMaxRedPedestrians()
                    && this.spawnedRedPedestrians <= this.spawnedBluePedestrians) {
                synchronized(this.pedestrianList) {
                    this.pedestrianList.add(new Pedestrian(p2.getId(), p2.getXCenter(), p2.getYCenter()));
                    this.spawnedRedPedestrians++;
                }
            }
        }
    }
}
