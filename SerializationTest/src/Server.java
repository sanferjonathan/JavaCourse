import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 420;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ServerSocket s = new ServerSocket(PORT);
        Socket socket = s.accept();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        while (true) {
            Data d3 = (Data) in.readObject();
            Data d4 = (Data) in.readObject();
            System.out.println("Object d1 is: " + d3);
            System.out.println("Object d2 is: " + d4);
            //d3.multiply(2);
            //d4.multiply(2);
            //out.writeObject(d3);
            //out.writeObject(d4);
        }
    }
}
