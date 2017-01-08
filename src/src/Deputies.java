package src;

import java.util.LinkedList;

public class Deputies {
	private LinkedList<Deputy> deputies;
	
	public Deputies() {
		deputies = new LinkedList<>();
	}
	
	public void addDeputy(Deputy deputy)
	{
		deputies.add(deputy);
	}
	
	public void print() {
		for(Deputy deputy : deputies)
		{
			System.out.println(deputy.toString());
		}
	}
}
