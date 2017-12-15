import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PearsonTest {
	
	private Pearson PearsonUnderTest;
	private Pearson PearsonUnderTest2;
	private Pearson PearsonUnderTest3;
	private Pearson PearsonUnderTest4;
	private Pearson PearsonUnderTest5;
	
	private Student PearsonUnderTest6;
	private Student PearsonUnderTest7;
	private Teacher PearsonUnderTest8;
	
	
	@Before
	public void setUp() throws Exception {
		PearsonUnderTest = new Student("lol", "yo", 1);
		PearsonUnderTest2 = new Student("lol", "yo", 2, "hej", "lei");
		PearsonUnderTest3 = new Teacher("lol", "yo", 3, "hej", "sej", "nej");
		PearsonUnderTest4 = new Teacher("mia", "li", 4, "hal", "sal", "kal");
		PearsonUnderTest5 = new Teacher("sia", "ni", 5);
		PearsonUnderTest6 = new Student("lal", "ya", 6);
		PearsonUnderTest7 = new Student("lwl", "yw", 7);
		PearsonUnderTest8 = new Teacher("lul", "yu", 8, "hej");
	}

	@Test
	public void testPearson() {
		assertEquals("lol", PearsonUnderTest.getName());
		assertEquals("yo", PearsonUnderTest.getSurname());
		assertEquals("1", PearsonUnderTest.getPersonNumber().toString());
		assertEquals(true, PearsonUnderTest3.knowsFact("hej"));
		assertEquals(true, PearsonUnderTest3.knowsFact("sej"));
		assertEquals(true, PearsonUnderTest3.knowsFact("nej"));
		
		assertEquals("mia", PearsonUnderTest4.getName());
		assertEquals("li", PearsonUnderTest4.getSurname());
		assertEquals("4", PearsonUnderTest4.getPersonNumber().toString());
		assertEquals(true, PearsonUnderTest4.knowsFact("hal"));
		assertEquals(true, PearsonUnderTest4.knowsFact("sal"));
		assertEquals(true, PearsonUnderTest4.knowsFact("kal"));
	}

	@Test
	public void testLearn() {
		assertEquals(false, PearsonUnderTest2.knowsFact("hej"));
		assertEquals(false, PearsonUnderTest2.knowsFact("lei"));
		assertEquals(false, PearsonUnderTest.listen(PearsonUnderTest, "hej"));
		assertEquals(true, PearsonUnderTest.listen(PearsonUnderTest3, "hej"));
		assertEquals(true, PearsonUnderTest.listen(PearsonUnderTest3, "sej"));
		assertEquals(true, PearsonUnderTest.listen(PearsonUnderTest3, "nej"));
		assertEquals(false, PearsonUnderTest.listen(PearsonUnderTest, "nej"));
		assertEquals(3, PearsonUnderTest.getNumberOfFacts());
		assertEquals(true, PearsonUnderTest.knowsFact("hej"));
		assertEquals(true, PearsonUnderTest.knowsFact("sej"));
		assertEquals(true, PearsonUnderTest.knowsFact("nej"));
		assertEquals(false, PearsonUnderTest.knowsFact("hs"));
		
		assertEquals(true, PearsonUnderTest3.learn("I can learn!!"));
		assertEquals(true, PearsonUnderTest4.learn("I can learn 2!!"));
		assertEquals(true, PearsonUnderTest3.knowsFact("I can learn!!"));
		assertEquals(true, PearsonUnderTest4.knowsFact("I can learn 2!!"));
		assertEquals("I can learn 2!!", PearsonUnderTest4.getList().get(3).contents);
	}

	@Test
	public void testListen() {
		assertEquals(true, PearsonUnderTest.listen(PearsonUnderTest3, "hej"));
		assertEquals(true, PearsonUnderTest.listen(PearsonUnderTest3, "sej"));
		assertEquals(true, PearsonUnderTest2.listen(PearsonUnderTest3, "hej"));
		assertEquals("hej", PearsonUnderTest.getList().get(0).contents);
		assertEquals("sej", PearsonUnderTest.getList().get(1).contents);
		assertEquals("hej", PearsonUnderTest2.getList().get(0).contents);
		assertEquals("accessible", PearsonUnderTest2.getList().get(0).factStatus.toString());
		
		assertEquals(true, PearsonUnderTest4.listen(PearsonUnderTest3, "hej"));
		assertEquals("hej", PearsonUnderTest4.getList().get(3).contents);
		assertEquals("kal", PearsonUnderTest4.getList().get(2).contents);
		assertEquals("sal", PearsonUnderTest4.getList().get(1).contents);
		assertEquals("hal", PearsonUnderTest4.getList().get(0).contents);
	}

	@Test
	public void testUpdateFact() {
		PearsonUnderTest.listen(PearsonUnderTest3, "hej");
		PearsonUnderTest4.listen(PearsonUnderTest3, "hej");
		PearsonUnderTest5.learn("hej");
		assertEquals(true, PearsonUnderTest4.knowsFact("hej"));
		assertEquals(true, PearsonUnderTest5.knowsFact("hej"));
		assertEquals("hej", PearsonUnderTest.speak());
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		PearsonUnderTest4.updateFactList();
		PearsonUnderTest4.updateFactList();
		PearsonUnderTest4.updateFactList();
		assertEquals("passive", PearsonUnderTest4.getList().get(0).factStatus.toString());
		assertEquals("passive", PearsonUnderTest.getList().get(0).factStatus.toString());
		PearsonUnderTest.updateFact("hej");
		assertEquals("accessible", PearsonUnderTest.getList().get(0).factStatus.toString());
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		PearsonUnderTest4.updateFactList();
		assertEquals("forgotten", PearsonUnderTest4.getList().get(3).factStatus.toString());
		assertEquals("forgotten", PearsonUnderTest.getList().get(0).factStatus.toString());
	}

	@Test
	public void testForgetFact() {
		PearsonUnderTest.listen(PearsonUnderTest3, "hej");
		PearsonUnderTest.listen(PearsonUnderTest3, "sej");
		PearsonUnderTest.listen(PearsonUnderTest3, "nej");
		assertEquals("accessible", PearsonUnderTest.getList().get(0).factStatus.toString());
		assertEquals("accessible", PearsonUnderTest.getList().get(1).factStatus.toString());
		assertEquals("accessible", PearsonUnderTest.getList().get(2).factStatus.toString());
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		assertEquals("passive", PearsonUnderTest.getList().get(0).factStatus.toString());
		assertEquals("passive", PearsonUnderTest.getList().get(1).factStatus.toString());
		assertEquals("passive", PearsonUnderTest.getList().get(2).factStatus.toString());
		PearsonUnderTest.updateFactList();
		assertEquals("forgotten", PearsonUnderTest.getList().get(0).factStatus.toString());
		assertEquals("forgotten", PearsonUnderTest.getList().get(1).factStatus.toString());
		assertEquals("forgotten", PearsonUnderTest.getList().get(2).factStatus.toString());
		PearsonUnderTest.updateFact("hej");
		assertEquals("accessible", PearsonUnderTest.getList().get(0).factStatus.toString());
		assertEquals("forgotten", PearsonUnderTest.getList().get(1).factStatus.toString());
		assertEquals("forgotten", PearsonUnderTest.getList().get(2).factStatus.toString());
		PearsonUnderTest.updateFactList();
		assertEquals(1, PearsonUnderTest.getList().size());
		PearsonUnderTest.listen(PearsonUnderTest3, "sej");
		assertEquals("accessible", PearsonUnderTest.getList().get(1).factStatus.toString());
		assertEquals(2, PearsonUnderTest.getList().size());
	}
	
	@Test
	public void testRegisterTeach() {
		PearsonUnderTest8.register(PearsonUnderTest6);
		PearsonUnderTest8.register(PearsonUnderTest7);
		PearsonUnderTest8.teach("hej");
		assertEquals(true, PearsonUnderTest6.knowsFact("hej"));
		assertEquals(true, PearsonUnderTest7.knowsFact("hej"));
		
		PearsonUnderTest3.register((Student)PearsonUnderTest);
		PearsonUnderTest3.register((Student)PearsonUnderTest2);
		PearsonUnderTest3.teach("hej");
		assertEquals(true, PearsonUnderTest.knowsFact("hej"));
		assertEquals(true, PearsonUnderTest2.knowsFact("hej"));
	}
}
