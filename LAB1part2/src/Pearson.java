import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Pearson {
	
	private final String name;
	private final String surname;
	private final String personalNumber;
	private int numberOfFacts;
	private List<Fact> list = new ArrayList<Fact>();
	
	public Pearson(String name, String surname, String personNumber, String... contents) {
		this.name = name;
		this.surname = surname;
		this.personalNumber = personNumber;
		this.numberOfFacts = 0;
		
		for (String word : contents) {
			learnFact(word);
		}
	}
	
	public enum FactStatus {
		accessible,
		passive,
		forgotten;
	}
	 
	protected class Fact {
		int counter;
		final String contents;
		String date;
		FactStatus factStatus;
		
		public Fact(String contents) {
			this.factStatus = FactStatus.accessible;
			this.counter = 0;
			this.contents = contents;
			this.date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		}
		
		public void update() {
			if(counter > 1 && this.factStatus == FactStatus.accessible) {
				this.factStatus = FactStatus.passive;
			}
			if(counter > 2 && this.factStatus == FactStatus.passive) {
				this.factStatus = FactStatus.forgotten;
			}
			this.counter++;
		}
	}
	
	protected boolean learnFact(String contents) {  
		 if(this.numberOfFacts < 100) {
			 this.list.add(new Fact(contents));
			 this.numberOfFacts++;
			 return true;
		 }
		 else {
			 System.out.printf("%s %s can't learn anything more! ", this.name, this.surname);
			 return false;
		 }
	}
	
	public boolean knowsFact(String contents) {
		for(Fact obj : this.list) {
			if(obj.contents.equals(contents)) {
				System.out.printf("%s\n", obj.factStatus);
				return true;
			}
		}
		return false;
	}
	

	
	public String speak() {
		return this.list.get(new Random().nextInt(this.numberOfFacts)).contents;
	}
	
	protected boolean listenTo(Pearson p, String msg) {
		if(p.knowsFact(msg) == true) {
			if(this.knowsFact(msg)) {
				this.updateFact(msg);
				return true;
			}
			else {
				this.learnFact(msg);
				return true;
			}
		}
		else {
			return false;
		}
	}
	
	public boolean updateFact(String msg) {
		for(Fact obj : this.list) {
			if(obj.contents.equals(msg)) {
				obj.date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
				obj.factStatus = FactStatus.accessible;
				obj.counter = 0;
				return true;
			}
		}
		return false;
	}
	
	public void updateFactList() {
		if (this.list.isEmpty()) {
			System.out.println("List empty!");
		}	
		else {
			Iterator<Fact> it = this.list.iterator();
			while (it.hasNext()) {
				Fact fact = it.next();
				fact.update();
			}
			forgetFact();
		}
	}
	
	protected void forgetFact() {
		Iterator<Fact> it = this.list.iterator();
		if(this.numberOfFacts > 0) {
			while (it.hasNext()) {
				if(it.next().counter > 4) {
					it.remove();
					System.out.println("Object deleted!");
					this.numberOfFacts--;
				}
			}
		}
		else {
			System.out.printf("%s %s does not know anything! ", this.name, this.surname);
		}
	}
	
	public int getNumberOfFacts() {
		return this.numberOfFacts;
	}
	public String getPersonNumber() {
		return this.personalNumber;
	}
	public String getName() {
		return this.name;
	}
	public String getSurname() {
		return this.surname;
	}
	public List<Fact> getList(){
		return this.list;
	}
}
