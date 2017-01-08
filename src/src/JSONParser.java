package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {
	
	static JSONObject toJSONobj(String path)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			StringBuilder sb = new StringBuilder();
			String line = new String();
			line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			line = sb.toString();
			return new JSONObject(line);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getDeputyID(String name,String surname,int cadency) throws IllegalArgumentException
	{
		int id = -1;
		int fileNumber = 0;
		JSONObject ob = JSONParser.toJSONobj("D:/" + ++fileNumber + "poslowie" + cadency +".json");
		while (id == -1 && ob.getJSONObject("Links").has("next")) {
			JSONArray deputies = (JSONArray) ob.get("Dataobject");
			for(int i=0;i<deputies.length();++i)
			{
				JSONObject deputy = deputies.getJSONObject(i);
				String jstring = (String) deputy.get("data").toString();
				JSONObject data = new JSONObject(jstring);
				if(name.equals(data.getString("poslowie.imie_pierwsze")) && surname.equals(data.getString("poslowie.nazwisko")))
				{
					id = data.getInt("poslowie.id");
				}
			}
			ob = JSONParser.toJSONobj("D:/" + ++fileNumber + "poslowie" + cadency +".json");
		}
		if(id==-1)
		{
			JSONArray deputies = (JSONArray) ob.get("Dataobject");
			for(int i=0;i<deputies.length();++i)
			{
				JSONObject deputy = deputies.getJSONObject(i);
				String jstring = (String) deputy.get("data").toString();
				JSONObject data = new JSONObject(jstring);
				if(name.equals(data.getString("poslowie.imie_pierwsze")) && surname.equals(data.getString("poslowie.nazwisko")))
				{
					id = data.getInt("poslowie.id");
				}
			}
		}
		if(id==-1) throw new IllegalArgumentException("brak posła o podanym imieniu i nazwisku");
		return id;
	}
	
}
