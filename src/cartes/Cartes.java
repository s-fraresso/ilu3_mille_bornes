package cartes;

public interface Cartes {
	public final Carte PRIORITAIRE = new Botte(Type.FEU); 
	public final Carte FEU_ROUGE = new Attaque(Type.FEU); 
	public final Carte FEU_VERT = new Parade(Type.FEU); 
}
