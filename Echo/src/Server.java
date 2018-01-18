import java.io.*;
import java.net.*;

public class Server {
	public static final int PORT = 1114;

	public static void main(String[] args) throws IOException {

		//Creating server socket
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server-socket: " + s);
		System.out.println("Server is listening...");
		Socket socket = s.accept();
		System.out.println("Connection accepted");
		System.out.println("The new socket: " + socket);

		//Creating reader object
		BufferedReader in = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));

		//Creating writer object
		PrintWriter out = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								socket.getOutputStream())), true);

		//Reads text from client and echoes it back
		while (true) {
			String inline = in.readLine();
			System.out.println("Server received: " + inline);
			if (inline == null || inline.equals("quit")) {
				out.println("Goodbye");
				socket.close();
				break;
			}
			out.println("You said '" + inline + "'");
		}
	}
}
