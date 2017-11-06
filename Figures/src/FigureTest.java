public class FigureTest {

	public static void main (String[] args) {
		Figure[] figs = new Figure[4];

		figs[0] = new Rectangle(10,10,10,10);
		figs[1] = new Circle(10,10,10);
		figs[2] = new Rectangle(20,20,20,20);
		figs[3] = new Circle(20,20,20);

		for (int index=0; index < figs.length; index++) {
			System.out.println(figs[index].getTitle());
			figs[index].printOut();
		}
	}

}
