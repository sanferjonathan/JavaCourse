package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerClient implements Runnable {

    protected Socket connection;
    String name;
    Integer id;

    DataInputStream inData;
    OutputStream outToClient;
    DataOutputStream outData;
    List<Pedestrian> pedestrianList = Collections
            .synchronizedList(new ArrayList<Pedestrian>());

    public ServerClient(Socket connection, String name, int id) throws IOException {
        this.connection = connection;
        this.name = name;
        this.id = id;

        this.inData = new DataInputStream(connection.getInputStream());
        this.outToClient = connection.getOutputStream();
        this.outData = new DataOutputStream(outToClient);
    }

    public void sendToClient(String message) throws IOException {
        this.outData.writeUTF(message);
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void UpdatePedestrianList(String[] pedestriansInfoList) throws IOException {
        if(pedestriansInfoList != null
                && pedestriansInfoList.length > 0
                && pedestriansInfoList[0] != "") {
            List<Pedestrian> tempList = Collections
                    .synchronizedList(new ArrayList<Pedestrian>());
            for (String line: pedestriansInfoList) {
                String[] vars = line.split(";");
                if(vars.length > 1) {
                    if(vars[0] != "" && vars[1] != ""
                            && vars[2] != "" && isNumeric(vars[0])
                            && isNumeric(vars[1]) && isNumeric(vars[2])) {
                        Pedestrian p = new Pedestrian(Integer.parseInt(vars[0]),
                                Double.parseDouble(vars[1]), Double.parseDouble(vars[2]));
                        synchronized(this.pedestrianList) {
                            tempList.add(p);
                        }
                    }
                }
            }
            if(tempList.size() >= 1) {
                this.pedestrianList.clear();
                this.pedestrianList = tempList;
            }
        }
    }

    public String[] receiveFromClient() throws IOException {
        String[] empty = null;
        String line;
        line = inData.readUTF();
        if (line == "done" || line == "") {
            this.pedestrianList.clear();
            return empty;
        }
        else {
            String[] pedestriansInfoList;
            pedestriansInfoList = line.split("/");
            return pedestriansInfoList;
        }
    }


    public void Listen() throws IOException {
        this.sendToClient("id" + this.id.toString());
        while(true) {
            String[] pedestriansInfoList = null;
            while (pedestriansInfoList == null) {
                pedestriansInfoList = this.receiveFromClient();
                if(pedestriansInfoList != null) {
                    this.UpdatePedestrianList(pedestriansInfoList);
                    break;
                }
            }
        }
    }


    public void run() {
        try {
            this.Listen();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
