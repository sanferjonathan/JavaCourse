import java.net.*;
import java.io.*;

public class Client {

	public static final int PORT = 1114;
	
	public static void main(String[] args) throws IOException {

		InetAddress address;
		if (args.length >= 1)
			address = InetAddress.getByName(args[0]);
		else
			address = InetAddress.getByName(null);

		//Creating socket
		Socket socket = new Socket(address, PORT);
		System.out.println("The new socket: " + socket);

		//Creating reader object to read answer from server
		BufferedReader in = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));

		//Creating reader object to read from keyboard
		BufferedReader kbd_reader = new BufferedReader(
				new InputStreamReader(System.in));

		//Creating writer object
		PrintWriter out = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream())), true);
		String buf;

		//Read from keyboard and send to server, then print answer from server.
		//Close connection if null or "quit" is read.
		while (true) {
			buf = kbd_reader.readLine();
			System.out.println("From keyboard: " + buf);
			System.out.println("To server: " + buf);
			out.println(buf);
			String inline = in.readLine();
			if (inline == null || inline == "Goodbye"){
				socket.close();
				break;
			}
			System.out.println("From server: " + inline);
			if (buf == null || buf.equals("quit")) {
				socket.close();
				break;
			}
		}
	} 
}
