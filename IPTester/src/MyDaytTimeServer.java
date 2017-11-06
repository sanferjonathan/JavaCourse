import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MyDaytTimeServer {
	public static final int PORT = 1313;
	public static void main (String[] args) throws IOException {
		try (ServerSocket s = new ServerSocket(PORT)){
			while (true) {
				try (Socket connection = s.accept()){
					Writer out = new OutputStreamWriter(connection.getOutputStream());
					Date now = new Date();
					out.write(now.toString() + "\r\n");
					out.flush();
					connection.close();
				} catch (IOException ex) {}
			}
		} catch (IOException ex) {
			System.err.println (ex);
		}
	}
}
