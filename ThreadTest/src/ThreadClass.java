import java.util.*;
public class ThreadClass implements Runnable{
	String name;
	int time;
	Random r = new Random();
	
	public ThreadClass(String x) {
		name = x;
		time = r.nextInt(999);
	}
	
	public void run() {
		try {
			System.out.printf("%s is sleeping for %d\n", name, time);
			Thread.sleep(time);
			System.out.printf("%s is done\n", name);
		}
		catch(Exception e) {
			System.out.println("Error");
		}
	}
}
