

public abstract class GeometricObject implements Comparable {
	protected int xCenter;
	protected int yCenter;

	public GeometricObject (int x, int y) {
		this.xCenter = x;
		this.yCenter = y;
		}

	public abstract int maxX();
	public abstract int maxY();
	public abstract int area();
	public abstract int perimeter();

	public void printOut() {
		System.out.println("    Highest x: " + maxX());
		System.out.println("    Highest y: " + maxY());
		System.out.println("    Area: " + area());
		System.out.println("    Perimeter: " + perimeter());
	}

	public abstract String getTitle();
}
