import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ObjectRun {
	
	public static void main (String[] args) {
		List<GeometricObject> figs = new ArrayList<GeometricObject>();

		figs.add(new Rectangle(10,10,10,10));
		figs.add(new Circle(15,15,15));
		figs.add(new Rectangle(20,20,20,20));
		figs.add(new Circle(30,30,30));

		for (int index = 0; index < figs.size(); index++) {
			System.out.println(figs.get(index).getTitle());
			figs.get(index).printOut();
			System.out.println("    Compare: " + figs.get(index).compare(5));
//			Collections.sort(figs, Comparator<figs.get(index).area()>);
		}
//		public Collection<fig> getList(){
//		    List<GeometricObject> fig = new ArrayList<GeometricObject>();
//		    Collections.sort(fig);
//		    return fig;
//		}
	}
}
