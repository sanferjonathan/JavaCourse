import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ObjectRun{
	
	public static void main (String[] args) {
		List<GeometricObject> list = new ArrayList<GeometricObject>();

		list.add(new Rectangle(10,10,10,10));
		list.add(new Circle(25,25,25));
		list.add(new Rectangle(20,20,20,20));
		list.add(new Circle(30,30,30));
		
//		Comparator<GeometricObject> comparator = new Comparator<GeometricObject>() {
//		    @Override
//		    public int compare(GeometricObject left, GeometricObject right) {
//		    	if(left.area() < right.area()) {
//		    		return right.area();
//		    	}
//		    	else {
//		    		return left.area();
//		    	}
//		    }
//		};
//		Collections.sort(list, comparator);

//		Collections.sort(list, (left, right) -> left.area() < right.area() ? left.area() : right.area());
//		list.sort((left, right) -> left.area() < right.area() ? left.area() : right.area());
		list.sort(Comparator.comparing(GeometricObject::area));
		System.out.println(list);

		for (int index = 0; index < list.size(); index++) {
			System.out.println(list.get(index).getTitle());
			list.get(index).printOut();
			System.out.println("    Distance: " + list.get(index).distanceToPoint(5, 7));
		}
	}
}
