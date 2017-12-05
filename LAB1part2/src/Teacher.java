import java.util.ArrayList;
import java.util.List;

public class Teacher extends Pearson {
	
	private List<Student> course = new ArrayList<Student>();
	private Integer numberOfStudents = 0;

	public Teacher(String name, String surname, Integer personalNumber, String... contents) {
		super(name, surname, personalNumber, contents);
		System.out.println("I am a Teacher!");
	}	
	
	protected boolean listen(Pearson p, String msg) {
		if(p.knowsFact(msg) == true) {
			if(this.knowsFact(msg)) {
				this.updateFact(msg);
				return true;
			}
			else {
				this.learn(msg);
				return true;
			}
		}
		else {
			return false;
		}
	}
	
	protected boolean learn(String contents) {  
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
	
	public void register(Student student) {
		if(this.numberOfStudents < 100) {
			this.course.add(student);
			this.numberOfStudents++;
		}
		else {
			System.out.println("This course is full!");
		}
	}
	
	public void teach(String contents) {
		for(Pearson pearson : this.course) {
			pearson.listen(this, contents);
		}
	}
	
	public List<Student> getCourse() {
		return this.course;
	}
}








