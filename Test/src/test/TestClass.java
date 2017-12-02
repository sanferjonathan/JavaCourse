package test;

import java.util.ArrayList;
import java.util.List;

public class TestClass {
	private Integer counter = 0;
	private List<Integer> list = new ArrayList<Integer>();
	
	public List<Integer> getList() {
		return list;
	}
	
	public void addInt(Integer num) {
		this.list.add(num);
		this.counter++;
	}
	
	public void removeInt(int index) {
		this.list.remove(index);
		this.counter--;
	}
	public Integer getCounter() {
		return this.counter;
	}
}
