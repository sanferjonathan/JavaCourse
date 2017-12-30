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
            Listen();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Listen() throws IOException {
        while(true) {
            String[] pedestriansInfoList = null;
            while (pedestriansInfoList == null) {
                pedestriansInfoList = this.receiveFromServer();
                if(pedestriansInfoList != null) {
                    this.pedestrianList.clear();
                    this.UpdatePedestrianList(pedestriansInfoList);
                    System.out.println("klient har tagit emot");
                    break;
                }
            }
            this.updateVelocity();
            this.onUpdate();
            this.sendToServer(this.GeneratePedestrianList());
        }
    }

    public String[] receiveFromServer() throws IOException {
        DataInputStream inData;
        inData = new DataInputStream(connection.getInputStream());

        String pedestriansInfo = null;
        while (inData.available() > 0) {
            pedestriansInfo = inData.readUTF();
        }

        String[] pedestriansInfoList;
        if (pedestriansInfo != null) {
            if(pedestriansInfo.contains("id")) {
                setId(pedestriansInfo);
            }
            else {
                System.out.println("Indata: " + pedestriansInfo + "end");
                pedestriansInfoList = pedestriansInfo.split("/");
                System.out.println("OK:" + pedestriansInfoList[0]
                        + " last: " + pedestriansInfoList[pedestriansInfoList.length-1]);
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
        System.out.println("Got ID from server:" + id);
    }

    public void UpdatePedestrianList(String[] pedestriansInfoList) throws IOException {
        for (String line : pedestriansInfoList) {
            String[] vars = line.split(";");
            Integer id = Integer.parseInt(vars[0]);
            Double x = Double.parseDouble(vars[1]);
            Double y =  Double.parseDouble(vars[2]);
            Pedestrian p = new Pedestrian(id, x, y);
            this.pedestrianList.add(p);
        }
    }

    public void updateVelocity() {
        for(int i = 0; i < pedestrianList.size(); i++) {
            for (int j = 0; j < pedestrianList.size(); j++) {
                if (i != j) {
                    //pedestrianList.get(i).defaultVelocity();
                    if(pedestrianList.get(i).id == this.id) {
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
    }

    public void onUpdate() {
        for(Pedestrian pedestrian : this.pedestrianList) {
            pedestrian.update();
        }
    }

    private String GeneratePedestrianList() {
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
        DataOutputStream outData;
        outData = new DataOutputStream(connection.getOutputStream());

        outData.writeUTF(message);
        System.out.println("Skickat tbx detta: " + message);
    }
}
