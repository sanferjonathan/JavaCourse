
public class Presenter extends Thread {
	private String msg;
	private int sleepTime;
	private int steps;

	public Presenter(String message, int dur) {
		this.msg = message;
		this.sleepTime = dur;
		this.steps = 0;
		this.start();
	}

	public void run () {
		//while (true) {
		for (int z=0; z<10; z++) {
			for (int i=0; i<this.steps; i++) {
				System.out.print(" ");
			}
			this.steps++;
			System.out.println(this.msg);

			try {Thread.sleep((int)(this.sleepTime * 1000)); }
			catch (InterruptedException e) {
				System.out.println("Sombody woke me up!");
			}
		}
	}

	public static void main (String[] args){
		Presenter t1 = new Presenter("FirstThread", (int) 0.1);
		Presenter t2 = new Presenter("SecondThread", (int) 0.2);
		Presenter t3 = new Presenter("ThirdThread", (int) 0.3);
	}
}
