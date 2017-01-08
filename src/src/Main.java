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
				System.out.println(deputies.getLongestTravelTime());
				break;
			case najdrozszaPodroz:
				System.out.println(deputies.getMostExpensiveTravel());
				break;
			case najwiecejPodrozy:
				System.out.println(deputies.getMostTravels());
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