package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PedestrianClient implements Runnable{
    int port = 1337;
    Integer id = null;
    Socket connection;
    private Thread Trad;
    ArrayList < Pedestrian > pedestrianList = new ArrayList < Pedestrian > ();

    PedestrianClient() throws IOException {
        connection = new Socket(InetAddress.getByName(null), port);
    }

    public void SkickaTillServer(String message) throws IOException {
        OutputStream outToClient;
        DataOutputStream out;

        outToClient = connection.getOutputStream();
        out = new DataOutputStream(outToClient);
        out.writeUTF(message);
        System.out.println("Skickat tbx detta: " + message);
    }

    private String GeneratePedestrianList() {
        String pedestrianList = "";
        if(this.pedestrianList.size() > 1) {
            for (Pedestrian OnePedestrian: this.pedestrianList) {
                if(OnePedestrian.id == this.id) {
                    pedestrianList += OnePedestrian.id.toString() +
                            ";" + OnePedestrian.xCenter.toString() +
                            ";" + OnePedestrian.yCenter.toString() + "/";
                }
            }
        }
        else {
            return "done";
        }
        return pedestrianList;
    }


    public String[] TaEmotFranServer() throws IOException
    {
        DataInputStream din;
        String[] emptyList = null;
        String line = null;
        din = new DataInputStream(connection.getInputStream());

        while (din.available() > 0) {
            line = din.readUTF();
        }

        String pedestriansInfo;
        pedestriansInfo = line;

        String[] pedestriansInfoList;
        if (pedestriansInfo != null) {
            if(pedestriansInfo.contains("id")) {
                pedestriansInfo = pedestriansInfo.replace("id", "");
                int id = Integer.parseInt(pedestriansInfo);
                if(this.id == null)
                    this.id = id;
                //pedestriansInfo = null; Behövs ej?
                System.out.println("Got ID from server:" + id);
            }
            else {
                System.out.println("Indata: " + pedestriansInfo + "end");
                pedestriansInfoList = pedestriansInfo.split("/");
                System.out.println("OK:" + pedestriansInfoList[0]
                        + " last: " + pedestriansInfoList[pedestriansInfoList.length-1]);
                return pedestriansInfoList;
            }
        }
        return emptyList;
    }

    public void updateVelocity() {
        for(int i = 0; i < pedestrianList.size(); i++) {
            for (int j = 0; j < pedestrianList.size(); j++) {
                if (i != j) {
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


    public void UpdatePedestrianList(String[] pedestriansInfoList) throws IOException
    {
        if(pedestriansInfoList != null
                && pedestriansInfoList.length > 0
                && pedestriansInfoList[0] != null) {
            this.pedestrianList.clear();
            for (String line: pedestriansInfoList) {
                String[] vars = line.split(";");
                Integer id = Integer.parseInt(vars[0]);
                Double x = Double.parseDouble(vars[1]);
                Double y =  Double.parseDouble(vars[2]);
                Pedestrian p = new Pedestrian(id, x, y);
                this.pedestrianList.add(p);
            }
        }
    }

    public void Listen() throws IOException {
        while(true) {
            String[] pedestriansInfoList = null;
            while (pedestriansInfoList == null) {
                pedestriansInfoList = this.TaEmotFranServer();
                if(pedestriansInfoList != null) {
                    this.UpdatePedestrianList(pedestriansInfoList);
                    System.out.println("klient har tagit emot");
                    //pedestriansInfoList = null; Behövs ej?
                    break;
                }
            }
            this.Calculate(this.pedestrianList);
            this.SkickaTillServer(this.GeneratePedestrianList());
        }
    }

    public void Calculate(ArrayList pedestrians) { // måste ta in Arraylist utan att använda?
        System.out.println("Calculating() . . ");
        this.updateVelocity();
        this.onUpdate();
    }

    public void run() {
        try {
            Listen();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start () {
        if (Trad == null) {
            Trad = new Thread (this);
            Trad.start ();
        }
    }
}
