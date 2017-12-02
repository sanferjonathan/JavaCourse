import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PearsonTest {
	
	private Pearson PearsonUnderTest;
	private Pearson PearsonUnderTest2;
	private Pearson PearsonUnderTest3;
	
	@Before
	public void setUp() throws Exception {
		PearsonUnderTest = new Pearson("lol", "yo", "lo");
		PearsonUnderTest2 = new Pearson("lol", "yo", "lo");
		
	}

	@Test
	public void testPearson() {
		PearsonUnderTest3 = new Pearson("lol", "yo", "lo", "a", "b", "asdf");
		assertEquals("lol", PearsonUnderTest.getName());
		assertEquals("yo", PearsonUnderTest.getSurname());
		assertEquals("lo", PearsonUnderTest.getPersonNumber());
		assertEquals(true, PearsonUnderTest3.knowsFact("a"));
		assertEquals(true, PearsonUnderTest3.knowsFact("b"));
		assertEquals(true, PearsonUnderTest3.knowsFact("asdf"));
	}

	@Test
	public void testLearnFact() {
		assertEquals(true, PearsonUnderTest.learnFact("hej"));
		assertEquals(true, PearsonUnderTest.learnFact("hejsdfa"));
		assertEquals(true, PearsonUnderTest.learnFact("hsdfasdfaj"));
		assertEquals(3, PearsonUnderTest.getNumberOfFacts());
		assertEquals(true, PearsonUnderTest.knowsFact("hej"));
		assertEquals(true, PearsonUnderTest.knowsFact("hejsdfa"));
		assertEquals(true, PearsonUnderTest.knowsFact("hsdfasdfaj"));
		assertEquals(false, PearsonUnderTest.knowsFact("hs"));
	}

	@Test
	public void testListenTo() {
		PearsonUnderTest.learnFact("hej");
		assertEquals(false, PearsonUnderTest.listenTo(PearsonUnderTest2,"hej"));
		assertEquals(true, PearsonUnderTest2.listenTo(PearsonUnderTest,"hej"));
		assertEquals(true, PearsonUnderTest.listenTo(PearsonUnderTest2,"hej"));
		assertEquals("hej", PearsonUnderTest.getList().get(0).contents);
		assertEquals("hej", PearsonUnderTest2.getList().get(0).contents);
		assertEquals("accessible", PearsonUnderTest2.getList().get(0).factStatus.toString());
	}

	@Test
	public void testUpdateFact() {
		PearsonUnderTest.learnFact("hej");
		assertEquals("hej", PearsonUnderTest.speak());
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		assertEquals("passive", PearsonUnderTest.getList().get(0).factStatus.toString());
		PearsonUnderTest.updateFact("hej");
		assertEquals("accessible", PearsonUnderTest.getList().get(0).factStatus.toString());
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		PearsonUnderTest.updateFactList();
		assertEquals("forgotten", PearsonUnderTest.getList().get(0).factStatus.toString());
	}

	@Test
	public void testForgetFact() {
		PearsonUnderTest.learnFact("hej");
		PearsonUnderTest.learnFact("sej");
		PearsonUnderTest.learnFact("nej");
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
	}
}
