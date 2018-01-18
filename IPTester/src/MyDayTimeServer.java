import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MyDayTimeServer {
	public static final int PORT = 1337;
	public static void main (String[] args) throws IOException {
		try (ServerSocket s = new ServerSocket(PORT)){
			while (true) {
				try (Socket connection = s.accept()){
					Writer out = new OutputStreamWriter(
							connection.getOutputStream());
					Date now = new Date();
					out.write(now.toString() + "\r\n");
					out.flush();
					connection.close();
				} catch (IOException ex) {
					System.out.println("error :(");
				}
			}
		} catch (IOException ex) {
			System.err.println (ex);
		}
	}
}
