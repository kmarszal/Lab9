package src;

import java.util.ArrayList;
import java.util.List;

public class Deputies {
	private List<Deputy> deputies;
	
	public Deputies() {
		deputies = new ArrayList<>();
	}
	
	public void addDeputy(Deputy deputy)
	{
		deputies.add(deputy);
	}
	
	public Deputy getDeputyWithID(int id)
	{
		if(deputies.size()-1>id)
		{
			Deputy deputy = deputies.get(id);
			return deputy;
		}
		else
		{
			System.out.println("Brak id w liście");
			return null;
		}
		
	}
}
