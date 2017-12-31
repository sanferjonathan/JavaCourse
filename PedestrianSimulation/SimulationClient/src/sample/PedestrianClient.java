package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class PedestrianClient implements Runnable {
    int port = 1337;
    Integer id = null;
    Socket connection;
    private Thread thread;
    ArrayList<Pedestrian> pedestrianList = new ArrayList<>();

    PedestrianClient() throws IOException {
        connection = new Socket(InetAddress.getByName(null), port);
    }


    public void start () {
        if (thread == null) {
            thread = new Thread (this);
            thread.start();
        }
    }

    public void run() {
        try {
            listen();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() throws IOException {
        while(true) {
            String[] pedestriansInfoList = null;
            while (pedestriansInfoList == null) {
                pedestriansInfoList = this.receiveFromServer();
                if(pedestriansInfoList != null) {
                    this.pedestrianList.clear();
                    this.updatePedestrianList(pedestriansInfoList);
                    break;
                }
            }
            this.updateVelocity();
            this.onUpdate();
            this.sendToServer(this.generatePedestrianList());
        }
    }

    public String[] receiveFromServer() throws IOException {
        DataInputStream inData = new DataInputStream(connection.getInputStream());

        String pedestriansInfo = null;
        while (inData.available() > 0) {
            pedestriansInfo = inData.readUTF();
        }

        return handleInfoList(pedestriansInfo);
    }

    public String[] handleInfoList(String pedestriansInfo){
        String[] pedestriansInfoList;
        if (pedestriansInfo != null) {
            if(pedestriansInfo.contains("id")) {
                setId(pedestriansInfo);
            }
            else {
                pedestriansInfoList = pedestriansInfo.split("/");
                return pedestriansInfoList;
            }
        }
        String[] emptyList = null;
        return emptyList;
    }

    public void setId(String pedestriansInfo){
        pedestriansInfo = pedestriansInfo.replace("id", "");
        int id = Integer.parseInt(pedestriansInfo);
        if(this.id == null) {
            this.id = id;
        }
    }

    public void updatePedestrianList(String[] pedestriansInfoList) throws IOException {
        for (String section : pedestriansInfoList) {
            String[] elements = section.split(";");
            Integer id = Integer.parseInt(elements[0]);
            Double x = Double.parseDouble(elements[1]);
            Double y =  Double.parseDouble(elements[2]);
            Pedestrian pedestrian = new Pedestrian(id, x, y);
            this.pedestrianList.add(pedestrian);
        }
    }

    public void updateVelocity() {
        for(int i = 0; i < pedestrianList.size(); i++) {
            for (int j = 0; j < pedestrianList.size(); j++) {
                if(pedestrianList.get(i).id == this.id && i != j) {
                    if(j == pedestrianList.size() - 1) {
                        pedestrianList.get(i).scan(pedestrianList.get(j), true);
                    }
                    else {
                        pedestrianList.get(i).scan(pedestrianList.get(j), false);
                    }
                }
            }
        }
    }

    public void onUpdate() {
        for(Pedestrian pedestrian : this.pedestrianList) {
            pedestrian.update();
        }
    }

    private String generatePedestrianList() {
        String pedestrianList = "";
        if(this.pedestrianList.size() > 1) {
            for (Pedestrian pedestrian : this.pedestrianList) {
                if(pedestrian.id == this.id) {
                    pedestrianList += pedestrian.id.toString() +
                            ";" + pedestrian.xCenter.toString() +
                            ";" + pedestrian.yCenter.toString() + "/";
                }
            }
        }
        else {
            return "done";
        }
        return pedestrianList;
    }

    public void sendToServer(String message) throws IOException {
        DataOutputStream outData = new DataOutputStream(connection.getOutputStream());
        outData.writeUTF(message);
    }
}
