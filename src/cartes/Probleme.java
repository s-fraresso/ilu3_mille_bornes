package cartes;

public abstract class Probleme extends Carte {
	private Type type;

	protected Probleme(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		Probleme probleme = (Probleme) obj;
		return type.equals(probleme.getType());
	}
	
	@Override
	public int hashCode() {
		return 31 * getType().hashCode();
	}
}
