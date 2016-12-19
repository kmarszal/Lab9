package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
	
}
