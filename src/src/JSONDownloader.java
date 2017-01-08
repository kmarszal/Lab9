package src;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONDownloader {

	public static void download(String source, String Target, int count, boolean next) {
		int i = 0;
		while(Target.charAt(i)>'0' && Target.charAt(i)<'9') ++i;
		String TargetFullName = "D:/" + count + Target.substring(i);
		File file = new File(TargetFullName);
		if (!file.exists()) {
			System.out.println("downloading " + TargetFullName);
			Path target = file.toPath();
			try {
				URL website = new URL(source);
				try (InputStream in = website.openStream()) {
					Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("done");
				}
			} catch (MalformedURLException ex) {
				System.out.println(ex);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (next) {
			JSONObject ob = JSONParser.toJSONobj(TargetFullName);
			if (ob.getJSONObject("Links").has("next")) download(ob.getJSONObject("Links").getString("next"), TargetFullName.substring(TargetFullName.lastIndexOf('p')), ++count, true);
		}
	}

	public static void downloadAll(int cadency) {
		switch (cadency) {
		case 7:
		case 8:
			download("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]=" + cadency,
					"poslowie" + cadency + ".json", 1,true);
			break;
		default:
			download("https://api-v3.mojepanstwo.pl/dane/poslowie.json", "poslowie78.json", 1,true);
		}
	}

	public static void downloadExpenses(int deputyid) {
		download("https://api-v3.mojepanstwo.pl/dane/poslowie/" + deputyid + ".json?layers[]=krs&layers[]=wydatki","wydatki" + deputyid + ".json", 0, false);
	}
	
	public static void downloadTravels(int deputyid) {
		download("https://api-v3.mojepanstwo.pl/dane/poslowie/" + deputyid + ".json?layers[]=krs&layers[]=wyjazdy","wyjazdy" + deputyid + ".json", 0, false);
	}
	
	public static void downloadAllExpensesAndTravels(int cadency)
	{
		int fileNumber = 0;
		JSONObject ob = JSONParser.toJSONobj("D:/" + ++fileNumber + "poslowie" + cadency +".json");
		while (ob.getJSONObject("Links").has("next")) {
			JSONArray deputies = (JSONArray) ob.get("Dataobject");
			for(int i=0;i<deputies.length();++i)
			{
				JSONObject deputy = deputies.getJSONObject(i);
				String jstring = (String) deputy.get("data").toString();
				JSONObject data = new JSONObject(jstring);
				downloadExpenses(data.getInt("poslowie.id"));
				downloadTravels(data.getInt("poslowie.id"));
			}
			ob = JSONParser.toJSONobj("D:/" + fileNumber + "poslowie" + cadency +".json");
			++fileNumber;
		}
		JSONArray deputies = (JSONArray) ob.get("Dataobject");
		for(int i=0;i<deputies.length();++i)
		{
			JSONObject deputy = deputies.getJSONObject(i);
			String jstring = (String) deputy.get("data").toString();
			JSONObject data = new JSONObject(jstring);
			downloadExpenses(data.getInt("poslowie.id"));
			downloadTravels(data.getInt("poslowie.id"));
		}
	}
}
