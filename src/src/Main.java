package src;

public class Main {

	public static void main(String[] args) {
		try {
			UserOptions uo = ArgumentsParser.Parse(args);
			JSONDownloader.downloadAll(uo.getCadency());
			JSONDeputyList deputies = new JSONDeputyList(uo);
			
			switch (uo.getOptions()) {
			case wydatki:
				if (uo.getSurname() != null) {
					System.out.println(JSONDeputyList.getExpenses(uo));
				} else {
					System.out.println(deputies.getAverageExpenses());
				}
				break;
			case najdluzszePodroze:
				Deputy d = deputies.getLongestTravelTime();
				if(d.getId()==0) System.out.println("brak danych");
				else System.out.println(d);
				break;
			case najdrozszaPodroz:
				Deputy de = deputies.getMostExpensiveTravel();
				if(de.getId()==0) System.out.println("brak danych");
				else System.out.println(de);
				break;
			case najwiecejPodrozy:
				Deputy dep = deputies.getMostTravels();
				if(dep.getId()==0) System.out.println("brak danych");
				else System.out.println(dep);
				break;
			case naprawy:
				System.out.println(JSONDeputyList.getRepairExpenses(uo));
				break;
			case wlochy:
				deputies.getItalyVisitors().print();
				break;
			default:
				break;
			}
		} catch (IllegalArgumentException ex) {
			System.out.println(ex);
		}
	}
}