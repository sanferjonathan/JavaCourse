public class Rectangle extends Figure {
     private int side;
     private int height;
     
     public Rectangle (int x, int y, int s, int h) {
    	 super(x,y);
    	 this.side = s;
    	 this.height = h;
     }
     
     public String getTitle() {
 		return "Rectangle :";
 	}

	@Override
	public int maxX() {
		return super.xCenter + this.side/2;
	}

	@Override
	public int maxY() {
		return super.yCenter + this.height/2;
	}

	@Override
	public int area() {
		return this.height * this.side;
	}

	@Override
	public int perimeter() {
		return 2 * this.height +  2 * this.side;
	}
     
}
