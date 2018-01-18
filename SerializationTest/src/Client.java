import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private static final int PORT = 420;

    public static void main(String[] args) throws IOException {
        InetAddress inetAddress;
        inetAddress = InetAddress.getByName("localhost");
        Socket socket = new Socket(inetAddress, PORT);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Data d1;
        Data d2;
        while (true) {
            d1 = new Data(1);
            d2 = new Data(2);
            out.writeObject(d1);
            out.writeObject(d2);
        }
    }
}
