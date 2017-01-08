package src;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentsParser {
	public static UserOptions Parse(String[] args) throws IllegalArgumentException {
		String name = null;
		String surname = null;
		Options option = null;
		int cadency = 0;
		Pattern imieNazwisko = Pattern.compile("[[a-z][A-Z][źżąśęćńłó][ŻĄŚŹĆĘŃŁÓ][-]]*");
		Pattern naprawy = Pattern.compile("-.*r.*");
		Pattern wlochy = Pattern.compile("-.*i.*");
		Pattern najwiecejPodrozy = Pattern.compile("-.*q.*");
		Pattern najdluzejZaGranica = Pattern.compile("-.*l.*");
		Pattern najdrozszaPodroz = Pattern.compile("-.*e.*");
		Pattern kadencja = Pattern.compile("-.*[7-8].*");
		for (String a : args) {
			Matcher mImieNazwisko = imieNazwisko.matcher(a);
			Matcher mNaprawy = naprawy.matcher(a);
			Matcher mWlochy = wlochy.matcher(a);
			Matcher mNajwiecejPodrozy = najwiecejPodrozy.matcher(a);
			Matcher mNajdluzejZaGranica = najdluzejZaGranica.matcher(a);
			Matcher mNajdrozszaPodroz = najdrozszaPodroz.matcher(a);
			Matcher mKadencja = kadencja.matcher(a);
			if (mImieNazwisko.matches() && !a.startsWith("-")) {
				if (name == null && surname == null) name = a;
				else if (surname == null) surname = a;
				else throw new IllegalArgumentException("zbyt duzo argumentow"); 
			}
			if(checkOption(mNaprawy,option)) option = Options.naprawy;
			if(checkOption(mWlochy,option)) option = Options.wlochy;
			if(checkOption(mNajwiecejPodrozy,option)) option = Options.najwiecejPodrozy;
			if(checkOption(mNajdluzejZaGranica,option)) option = Options.najdluzszePodroze;
			if(checkOption(mNajdrozszaPodroz,option)) option = Options.najdrozszaPodroz;
			if(mKadencja.matches()) cadency = parseInt(a);
		}
		if(option == null) option = Options.wydatki;
		if(option != Options.naprawy && option != Options.wydatki && name != null)
		{
			System.out.println("Podano imię i opcję, która tego nie wymaga");
		}
		if(option == Options.naprawy && surname==null) throw new IllegalArgumentException("nie podano imienia i nazwiska posła");
		return new UserOptions(cadency, name, surname, option);
	}

	private static boolean checkOption(Matcher m,Options option) {
		if (option!=null && m.matches()) throw new IllegalArgumentException("zbyt duzo opcji");
		if (m.matches()) return true;
		else return false;
	}
	
	private static int parseInt(String arg)
	{
		int result = 0;
		for (char c : arg.toCharArray())
		{
			if(c > '0' && c < '9')
			{
				if(result != 0 ) throw new IllegalArgumentException("Nieprawidlowa kadencja");
				result = c - '0';
			}
		}
		return result;
	}
}
