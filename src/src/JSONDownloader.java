package src;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class JSONDownloader {

	public static void download(String source,String Target) {
		File file = new File(Target);
		if (!file.exists()) {
			Path target = new File(Target).toPath();
			try {
				URL website = new URL(source);
				try (InputStream in = website.openStream()) {
					Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
				}
			} catch (MalformedURLException ex) {
				System.out.println(ex);
			} catch (IOException ex) {
				System.out.println(ex);
			}
		}
	}
	
	public static void downloadAll()
	{
		download("https://api-v3.mojepanstwo.pl/dane/poslowie.json","poslowie78.json");
		download("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]=8","poslowie8.json");
	}
}
