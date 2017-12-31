package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerClient implements Runnable {

    String name;
    Integer id;
    DataInputStream inData;
    DataOutputStream outData;
    List<Pedestrian> pedestrianList = Collections
            .synchronizedList(new ArrayList<Pedestrian>());

    public ServerClient(Socket connection, String name, int id) throws IOException {
        this.name = name;
        this.id = id;

        this.inData = new DataInputStream(connection.getInputStream());
        this.outData = new DataOutputStream(connection.getOutputStream());
    }

    public void run() {
        try {
            this.listen();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() throws IOException {
        this.sendToClient("id" + this.id.toString());
        while(true) {
            String[] pedestriansInfoList = null;
            while (pedestriansInfoList == null) {
                pedestriansInfoList = this.receiveFromClient();
                if(pedestriansInfoList != null) {
                    this.createNewPedestrians(pedestriansInfoList);
                    break;
                }
            }
        }
    }

    public String[] receiveFromClient() throws IOException {
        String pedestriansInfo = inData.readUTF();
        if (pedestriansInfo == "done" || pedestriansInfo == "") {
            this.pedestrianList.clear();
            String[] empty = null;
            return empty;
        }
        else {
            String[] pedestriansInfoList;
            pedestriansInfoList = pedestriansInfo.split("/");
            return pedestriansInfoList;
        }
    }

    public void createNewPedestrians(String[] pedestriansInfoList) throws IOException {
        List<Pedestrian> list = Collections
                .synchronizedList(new ArrayList<Pedestrian>());
        for (String section : pedestriansInfoList) {
            String[] elements = section.split(";");
            if(checkElements(elements)) {
                Pedestrian pedestrian = new Pedestrian(Integer.parseInt(elements[0]),
                        Double.parseDouble(elements[1]), Double.parseDouble(elements[2]));
                synchronized(this.pedestrianList) {
                    list.add(pedestrian);
                }
            }
        }
        updatePedestrianList(list);
    }

    public boolean checkElements(String[] elements){
        return elements[0] != "" && elements[1] != ""
                && elements[2] != "" && isNumeric(elements[0])
                && isNumeric(elements[1]) && isNumeric(elements[2])
                && elements.length > 1;
    }

    public void updatePedestrianList(List<Pedestrian> list){
        if(list.size() >= 1) {
            this.pedestrianList.clear();
            this.pedestrianList = list;
        }
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
}
