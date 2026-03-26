package cartes;

public interface Cartes {
	public Carte PRIORITAIRE = new Botte(Type.FEU); 
	public Carte FEU_ROUGE = new Attaque(Type.FEU); 
	public Carte FEU_VERT = new Parade(Type.FEU); 
}
