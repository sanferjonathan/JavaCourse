package test;
import java.util.Iterator;

public class mainTest {

	public static void main(String[] args) {
		
		TestClass a = new TestClass();
		
		for (int i = 0; i < 10; i++) {
			a.addInt(i);
			System.out.println(a.getList().get(i));
		}
		
		Iterator<Integer> it = a.getList().iterator();
		while (it.hasNext()) {
		    Integer integer = it.next();
		    if (integer % 2 == 0) {
		        it.remove();
		    }
		}
		System.out.println(a.getList());
	}
}
