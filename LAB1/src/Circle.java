public class Circle extends GeometricObject {
	private int radius;

	public Circle (int x, int y, int r) {
		super(x,y);
		this.radius = r;
	}

	public int maxX() { return super.xCenter + this.radius; }
	public int maxY() { return super.yCenter + this.radius; }
	public int area() {
		return (int) (Math.PI * this.radius * this.radius);
	}

	public int perimeter() {
		return (int) (2 * Math.PI * this.radius);
	}

	public void printOut() {
		System.out.println("    Diameter :" + this.diameter());
		super.printOut();
	}

	public String getTitle() {
		return "Circle :";
	}

	public int diameter() {
		return this.radius + this.radius;
	}

	@Override
	public double distanceToPoint(int x, int y) {
		return Math.sqrt(Math.pow(x - this.xCenter, 2) + Math.pow(y - this.yCenter, 2));
	}
}
