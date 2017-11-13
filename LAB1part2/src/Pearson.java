import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Pearson {
	
	private String name;
	private String surname;
	private String personalNumber;
	private int numberOffacts;
	private List<Fact> list;
	
	private void Person(String name, String surname, String personNumber) {
		this.name = name;
		this.surname = surname;
		this.personalNumber = personNumber;
		this.numberOffacts = 0;
		
		System.out.println("Select the number of facts this person is born with!");
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Give the contents and date of each fact!");
		for (int i = scanner.nextInt(); i > 0; i--) {
			addFact(new Fact(scanner.nextLine(), scanner.nextLine()));
		}
		scanner.close();
	}
	
	public enum FactStatus {
		accessible,
		passive,
		forgotten;
	}
	 
	protected class Fact {
		int counter;
		String contents;
		String date;
		FactStatus factStatus;
		
		public Fact(String contents, String date) {
			this.factStatus = FactStatus.accessible;
			this.counter = 0;
			this.contents = contents;
			this.date = date;
		}
		
		public void update() {
			counter++;
			if(counter > 3 && this.factStatus == FactStatus.accessible) {
				this.factStatus = FactStatus.passive;
			}
			if(counter > 6 && this.factStatus == FactStatus.passive) {
				this.factStatus = FactStatus.forgotten;
			}
			if(counter > 10 && this.factStatus == FactStatus.forgotten) {
				removeFact(this);
			}
		}
	}
	
	public void addFact(Fact fact) {  
		 if(this.numberOffacts < 100) {
			 this.list.add(fact);
			 this.numberOffacts++;
		 }
		 else {
			 System.out.printf("%s %s can't learn anything more!", this.name, this.surname);
		 }
	}
	
	public void removeFact(Fact fact) {
		if(this.numberOffacts > 0) {
			this.list.remove(fact);
			this.numberOffacts--;
		}
		else {
			System.out.printf("%s %s does not know anything!", this.name, this.surname);
		}
	}
	
	public boolean knowsFact(String contents) {
		for(Fact obj : list) {
			if(obj.contents.equals(contents)) {
				System.out.printf("%s", obj.factStatus);
				return true;
			}
		}
		return false;
	}
	
	public String personNumber() {
		return this.personalNumber;
	}
	
	public String speak() {
		Random rand = new Random();
		return list.get(rand.nextInt(this.numberOffacts)).contents;
	}
}
