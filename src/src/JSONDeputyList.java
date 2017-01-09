package src;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONDeputyList {
	private int cadency;
	private LinkedList<JSONObject> deputies;
	
	public JSONDeputyList(UserOptions uo)
	{
		cadency = uo.getCadency();
		deputies = new LinkedList<>();
		JSONDownloader.downloadAllExpensesAndTravels(uo.getCadency());
		int fileNumber = 0;
		JSONObject ob = JSONParser.toJSONobj("D:/" + ++fileNumber + "poslowie" + uo.getCadency() +".json");
		boolean prevhasnext = true;
		while(prevhasnext)
		{
			JSONArray deputies = (JSONArray) ob.get("Dataobject");
			if(uo.getOptions().equals(Options.wydatki))
			{
				for(int i=0;i<deputies.length();++i)
				{
					JSONObject data = new JSONObject(deputies.getJSONObject(i).get("data").toString());
					JSONObject deputy = JSONParser.toJSONobj("D:/0wydatki" + data.getInt("poslowie.id") + ".json");
					this.deputies.add(deputy);
				}
			}
			else
			{
				for(int i=0;i<deputies.length();++i)
				{
					JSONObject data = new JSONObject(deputies.getJSONObject(i).get("data").toString());
					JSONObject deputy = JSONParser.toJSONobj("D:/0wyjazdy" + data.getInt("poslowie.id") + ".json");
					this.deputies.add(deputy);
				}
			}
			prevhasnext = ob.getJSONObject("Links").has("next");
			if(prevhasnext) ob = JSONParser.toJSONobj("D:/" + ++fileNumber + "poslowie" + uo.getCadency() +".json");
		}
	}
	
	public Deputies getItalyVisitors()
	{
		Deputies deputylist = new Deputies();
			for(JSONObject deputy : deputies)
			{
				Object owyjazdy = deputy.getJSONObject("layers").get("wyjazdy");
				if(owyjazdy instanceof JSONArray)
				{
					if(((JSONArray) owyjazdy).toList().stream().map(x -> (HashMap<String,String>) x).map(x -> x.get("country_code")).anyMatch(x -> x.equals("IT")))
					{
						Deputy dep = new Deputy(deputy.getJSONObject("data").getString("poslowie.imie_pierwsze"),deputy.getJSONObject("data").getString("poslowie.nazwisko"),deputy.getJSONObject("data").getInt("poslowie.id"));
						deputylist.addDeputy(dep);
					}
				}
			}
		return deputylist;
	}
	
	public Deputy getMostTravels()
	{
		Deputy max = new Deputy("","",0);
		long maxtravels = 0;
			for(JSONObject deputy : deputies)
			{
				Object owyjazdy = deputy.getJSONObject("layers").get("wyjazdy");
				if(owyjazdy instanceof JSONArray)
				{
					long travels = ((JSONArray) owyjazdy).toList().stream().map(x -> (HashMap<String,String>) x).count();
					if(maxtravels < travels) {
						maxtravels = travels;
						max = new Deputy(deputy.getJSONObject("data").getString("poslowie.imie_pierwsze"),deputy.getJSONObject("data").getString("poslowie.nazwisko"),deputy.getJSONObject("data").getInt("poslowie.id"));
					}
				}
			}
		return max;
	}
	
	public Deputy getMostExpensiveTravel()
	{
		Deputy max = new Deputy("","",0);
		double maxexpense = 0;
		for(JSONObject deputy : deputies)
		{
			Object owyjazdy = deputy.getJSONObject("layers").get("wyjazdy");
			if(owyjazdy instanceof JSONArray)
			{
				Optional<Double> expense;
				expense = ((JSONArray) owyjazdy).toList().stream().map(x -> (HashMap<String,String>) x).map(x -> x.get("koszt_suma")).map(x -> Double.parseDouble(x)).max((Double x,Double y) -> x.compareTo(y));
				if(maxexpense < expense.get()) {
					maxexpense = expense.get();
					max = new Deputy(deputy.getJSONObject("data").getString("poslowie.imie_pierwsze"),deputy.getJSONObject("data").getString("poslowie.nazwisko"),deputy.getJSONObject("data").getInt("poslowie.id"));
				}
			}
		}
		return max;
	}
	
	public Deputy getLongestTravelTime()
	{
		Deputy max = new Deputy("","",0);
		int maxdays = 0;
		for(JSONObject deputy : deputies)
		{
			int days;
			Object owyjazdy = deputy.getJSONObject("layers").get("wyjazdy");
			if(owyjazdy instanceof JSONArray)
			{
				days = ((JSONArray) owyjazdy).toList().stream().map(x -> (HashMap<String,String>) x).map(x -> x.get("liczba_dni")).map(x -> Integer.parseInt(x)).reduce(0, (x,y) -> x + y);
				if(maxdays < days) {
					maxdays = days;
					max = new Deputy(deputy.getJSONObject("data").getString("poslowie.imie_pierwsze"),deputy.getJSONObject("data").getString("poslowie.nazwisko"),deputy.getJSONObject("data").getInt("poslowie.id"));
				}
			}
		}
		System.out.println(maxdays);
		return max;
	}
	
	public Double getAverageExpenses()
	{
		int deputycounter = 0;
		double sum = 0;
		for(JSONObject deputy : deputies)
		{
			JSONObject wydatki = deputy.getJSONObject("layers").getJSONObject("wydatki");
			JSONArray roczniki = wydatki.getJSONArray("roczniki");
			double deputysum = 0;
			for(int j=0;j<wydatki.getInt("liczba_rocznikow");++j)
			{
				deputysum += roczniki.getJSONObject(j).getJSONArray("pola").toList().stream().map((x) -> (String) x).map((x) -> Double.parseDouble(x)).reduce(0.0, (a,b) -> a + b);
			}
			sum += deputysum;
			++deputycounter;
		}
		return sum/deputycounter;
	}
	
	public static double getExpenses(UserOptions userOptions)
	{
		int id = JSONParser.getDeputyID(userOptions.getName(), userOptions.getSurname(), userOptions.getCadency());
		JSONDownloader.downloadExpenses(id);
		JSONObject deputy = JSONParser.toJSONobj("D:/0wydatki" + id + ".json");
		JSONObject wydatki = deputy.getJSONObject("layers").getJSONObject("wydatki");
		JSONArray roczniki = wydatki.getJSONArray("roczniki");
		double sum = 0;
		for(int i=0;i<wydatki.getInt("liczba_rocznikow");++i)
		{
			//(roczniki.getJSONObject(i).getInt("rok"));
			sum += roczniki.getJSONObject(i).getJSONArray("pola").toList().stream().map((x) -> (String) x).map((x) -> Double.parseDouble(x)).reduce(0.0, (a,b) -> a + b);
		}
		return sum;
	}
	
	public static double getRepairExpenses(UserOptions userOptions)
	{
		int id = JSONParser.getDeputyID(userOptions.getName(), userOptions.getSurname(), userOptions.getCadency());
		JSONDownloader.downloadExpenses(id);
		JSONObject wydatki = JSONParser.toJSONobj("D:/0wydatki" + id + ".json").getJSONObject("layers").getJSONObject("wydatki");
		JSONArray roczniki = wydatki.getJSONArray("roczniki");
		double sum = 0;
		for(int i=0;i<wydatki.getInt("liczba_rocznikow");++i)
		{
			sum += roczniki.getJSONObject(i).getJSONArray("pola").getDouble(13);
		}
		return sum;
	}
}
