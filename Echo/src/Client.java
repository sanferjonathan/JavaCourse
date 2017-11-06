import java.net.*;
import java.io.*;

public class Client {
public static final int PORT = 1114;
	
	public static void main(String[] args) throws IOException {
		InetAddress addr;
		if (args.length >= 1)
			addr = InetAddress.getByName(args[0]);
		else
			addr = InetAddress.getByName(null);
		Socket socket = new Socket(addr, PORT);
		System.out.println("The new socket: " + socket);
		
		BufferedReader in = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								socket.getOutputStream())), true);
		//true: PrintWriter is line buffered
		BufferedReader kbd_reader = new BufferedReader(
				new InputStreamReader(System.in));
		String buf;
		
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
