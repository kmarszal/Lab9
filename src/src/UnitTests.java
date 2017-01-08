package src;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

public class UnitTests {

	@Test
	public void arugmentParserTest() {
		try
		{
			String[] args = {"Jan", "Nowak", "-r7"};
			UserOptions uo = ArgumentsParser.Parse(args);
			UserOptions correct = new UserOptions(7,"Jan","Nowak",Options.naprawy);
			assertEquals(uo, correct);
			String[] args1 = {"-i9", "JAN", "NOWAK"};
			uo = ArgumentsParser.Parse(args1);
			correct = new UserOptions(78,"Jan","Nowak",Options.wlochy);
			assertEquals(uo, correct);
			String[] args2 = {"JaNusz", "KorWin-mikkE", "-q"};
			uo = ArgumentsParser.Parse(args2);
			correct = new UserOptions(78,"Janusz","Korwin-Mikke",Options.najwiecejPodrozy);
			assertEquals(uo, correct);
			String[] args3 = {"jan"};
			uo = ArgumentsParser.Parse(args3);
			correct = new UserOptions(78,"Jan",null,Options.wydatki);
			assertEquals(uo, correct);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void JSONParserTest() {
		try
		{
			JSONObject id118 = JSONParser.toJSONobj("D:/0wydatki118.json");
			assertEquals(id118.getJSONObject("data").getString("poslowie.nazwisko"), "Grad");
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void JSONParserGetIDTest() {
		try
		{
			assertEquals(JSONParser.getDeputyID("Mariusz", "Grad", 78), 118);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void deputyListFunctions() {
		UserOptions uo = new UserOptions(78,"Mariusz","Grad",Options.wydatki);
		JSONDeputyList.getExpenses(uo);
		JSONDeputyList.getRepairExpenses(uo);
		UserOptions uow = new UserOptions(78,null,null,Options.wydatki);
		JSONDeputyList ListWydatki = new JSONDeputyList(uow);
		System.out.println(ListWydatki.getAverageExpenses());
		JSONDeputyList ListPodroze = new JSONDeputyList(new UserOptions(78,null,null,Options.najdluzszePodroze));
		System.out.println(ListPodroze.getLongestTravelTime());
		System.out.println(ListPodroze.getMostTravels());
		System.out.println(ListPodroze.getMostExpensiveTravel());
		ListPodroze.getItalyVisitors().print();
		
	}
	
	
}
