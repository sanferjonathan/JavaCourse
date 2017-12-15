
public class Student extends Pearson {

	public Student(String name, String surname, Integer personalNumber, String... contents) {
		super(name, surname, personalNumber, contents);
	}
	
	protected boolean listen(Pearson p, String msg) {
		if(p.knowsFact(msg) == true) {
			Fact fact = new Fact(msg);
			if(p.getClass().isInstance(this)) {
				this.learn(msg);
				return false;
			}
			else {
				if(this.knowsFact(msg)) {
					this.updateFact(msg);
					return true;
				}
				else {
//					learnByListen(fact, msg);
					if(this.numberOfFacts < 100) {
						Learn learn = new Learn(fact, list) {
							@Override
							protected boolean learn(Fact fact) {
								this.list.add(fact);
								return true;
							}
						};
						System.out.printf("%s %s have learned fact %s!\n", this.name, this.surname, msg);
						this.numberOfFacts++;
						return learn.learn(fact);
					}
					else {
						 System.out.printf("%s %s can't learn anything more! ", this.name, this.surname);
						 return false;
					}
				}
			}
		}
		System.out.printf("that pearson does not know the fact %s!\n", msg);
		return false;
	}
	
	public boolean learnByListen(Fact fact, String msg) {
		if(this.numberOfFacts < 100) {
			Learn learn = new Learn(fact, list) {
				@Override
				protected boolean learn(Fact fact) {
					this.list.add(fact);
					return true;
				}
			};
			System.out.printf("%s %s have learned fact %s!\n", this.name, this.surname, msg);
			this.numberOfFacts++;
			return learn.learn(fact);
		}
		else {
			 System.out.printf("%s %s can't learn anything more! ", this.name, this.surname);
			 return false;
		}
	}
	
	protected boolean learn(String contents) {  
		 System.out.printf("%s %s can't learn unless listening to a teacher!\n", this.name, this.surname);
		 return false;
	}

	@Override
	public void register(Student student) {
		System.out.println("Only a teacher can register!");
	}

	@Override
	public void teach(String contents) {
		System.out.println("Only a teacher can teach!");
	}
}












