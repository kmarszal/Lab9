package src;

public class UserOptions {
	private int kadencja;
	private String imie;
	private String nazwisko;
	private boolean remonty;
	private boolean podroze;
	private boolean wlochy;
	
	public UserOptions(int kadencja, String imie, String nazwisko, boolean remonty, boolean podroze, boolean wlochy) {
		super();
		this.kadencja = kadencja;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.remonty = remonty;
		this.podroze = podroze;
		this.wlochy = wlochy;
	}
	
	public int getKadencja() {
		return kadencja;
	}
	public String getImie() {
		return imie;
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public boolean isRemonty() {
		return remonty;
	}
	public boolean isPodroze() {
		return podroze;
	}
	public boolean isWlochy() {
		return wlochy;
	}
	
	
	
}


/*
suma wydatków posła/posłanki o określonym imieniu i nazwisku
wysokości wydatków na 'drobne naprawy i remonty biura poselskiego' określonego posła/posłanki
średniej wartości sumy wydatków wszystkich posłów
posła/posłanki, który wykonał najwięcej podróży zagranicznych
posła/posłanki, który najdłużej przebywał za granicą
posła/posłanki, który odbył najdroższą podróż zagraniczną
listę wszystkich posłów, którzy odwiedzili Włochy
*/