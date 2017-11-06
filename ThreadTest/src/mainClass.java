
public class mainClass {
	public static void main(String[] args) {
		
		Thread t1 = new Thread(new ThreadClass("one"));
		Thread t2 = new Thread(new ThreadClass("two"));
		Thread t3 = new Thread(new ThreadClass("three"));
		Thread t4 = new Thread(new ThreadClass("four"));
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}
