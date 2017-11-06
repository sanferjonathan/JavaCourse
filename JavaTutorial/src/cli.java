import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class cli {
	public static void main(String[] args) throws UnknownHostException, IOException {
		int number, temp;
		Socket s = new Socket("127.0.0.1", 1342); //created a socket
		
		System.out.println("Enther any number");
		Scanner sc = new Scanner(System.in); //scanner to accept input from user
		number = sc.nextInt(); //accept number from user and store it in the variable number
		
		PrintStream p = new PrintStream(s.getOutputStream()); //Print stream object for sending number to server
		p.println(number); // here we send the number using the object
		
		Scanner sc1 = new Scanner(s.getInputStream()); // scanner that gets the output from the server3
		temp = sc1.nextInt(); //we use the sc1 object to get the number from the server
		System.out.println(temp); //print the result we get from the server
		
		s.close();
		sc.close();
		sc1.close();
	}
}
