import java.util.ArrayList;
import java.util.List;

public class Pearson {
	
	private String name;
	private String surname;
	private int personalNumber;
	private int numberOffacts;
	private List<Fact> list;
	
	private void Person(String name, String surname, int number) {
		this.name = name;
		this.surname = surname;
		this.personalNumber = number;
	}
	
	public void addFact(Fact fact) {  
		 this.list.add(fact);
	}
	 
	protected class Fact {
		String contents;
		String date;
		FactStatus factStatus; 
	}
	
	public enum FactStatus {
		newKnowledge,
		passiveKnowledge,
		forgottenKnowledge;
	}
}
