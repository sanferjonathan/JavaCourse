package sample;

import java.io.*;


public class Main {
    public static void main(String [] args) throws IOException {
        PedestrianClient clientThread = new PedestrianClient();
        clientThread.start();
    }
}

