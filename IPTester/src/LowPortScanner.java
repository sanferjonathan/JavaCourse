import java.net.*;
import java.io.*;

public class LowPortScanner {
	public static void main(String[] args) {
		//String host = args.length > 0 ? args[0] : "localhost";
		String host = "localhost";
		for (int i = 1; i < 1024; i++) {
			try {
				Socket mySocket = new Socket(host, 1337);
				System.out.println("There is a server!!");
				mySocket.close();
			} catch (UnknownHostException ex) {
				System.err.println(ex);
				break;
			} catch (IOException ex) {
				System.out.println("There must be no server on port " + i + " of "
						+ host);

			}
		}
	}
}
