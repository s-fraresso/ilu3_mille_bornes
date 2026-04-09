package cartes;

public class Borne extends Carte {
	private int km;
	
	public Borne(int km) {
		this.km = km;
	}
	
	public int getKm() {
		return km;
	}

	@Override
	public String toString() {
		return Integer.toString(km) + "KM";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		Borne borne = (Borne) obj;
		return km == borne.km;
	}
	
	@Override
	public int hashCode() {
		return 41 * km;
	}
}
