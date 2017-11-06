import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ser {
	public static void main(String[] args) throws IOException {
		int number, temp;
		ServerSocket s1 = new ServerSocket(1342); //creates a server socket that specifies the port number
		Socket ss = s1.accept(); //accepting incoming request to socket s1
		
		Scanner sc = new Scanner(ss.getInputStream()); //sc will accept the number that the client wants to pass
		number = sc.nextInt();
		
		temp = number * 2;
		
		PrintStream p = new PrintStream(ss.getOutputStream());
		p.println(temp);
		
		s1.close();
		sc.close();
	}
}
