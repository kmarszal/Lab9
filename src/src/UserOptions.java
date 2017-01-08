package src;

public class UserOptions {
	private int kadencja;
	private String imie;
	private String nazwisko;
	private Options opcja;
	
	public UserOptions(int cadency, String name, String surname, Options option) {
		if(cadency!=7 && cadency !=8) this.kadencja = 78;
		else this.kadencja = cadency;
		this.imie = name;
		this.imie = format(this.imie);
		this.nazwisko = surname;
		this.nazwisko = format(this.nazwisko);
		this.opcja = option;
	}
	
	public int getCadency() {
		return kadencja;
	}
	public String getName() {
		return imie;
	}
	public String getSurname() {
		return nazwisko;
	}
	public Options getOptions() {
		return opcja;
	}

	@Override
	public String toString() {
		return "UserOptions [kadencja=" + kadencja + ", imie=" + imie + ", nazwisko=" + nazwisko + ", opcja=" + opcja
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserOptions other = (UserOptions) obj;
		if (imie == null) {
			if (other.imie != null)
				return false;
		} else if (!imie.equals(other.imie))
			return false;
		if (kadencja != other.kadencja)
			return false;
		if (nazwisko == null) {
			if (other.nazwisko != null)
				return false;
		} else if (!nazwisko.equals(other.nazwisko))
			return false;
		if (opcja != other.opcja)
			return false;
		return true;
	}

	public String format(String str)
	{
		if(str!=null)
		{
			str = str.toLowerCase();
			String head = str.substring(0, 1);
			String tail = str.substring(1);
			head = head.toUpperCase();
			tail = tail.toLowerCase();
			if(tail.contains("-"))
			{
				String head1 = tail.substring(0,tail.indexOf('-'));
				String tail1 = tail.substring(tail.indexOf('-') + 1);
				tail1 = "-" + tail1.substring(0,1).toUpperCase() + tail1.substring(1);
				tail = head1+tail1;
			}
			return (head + tail);
		}
		else return str;
	}
}
