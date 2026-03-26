package jeu;

public class Joueur {
	private String nom;
	private ZoneDeJeu zoneDeJeu;
	
	public Joueur(String nom, ZoneDeJeu zoneDeJeu) {
		this.nom = nom;
		this.zoneDeJeu = zoneDeJeu;
	}
	
	public String getNom() {
		return nom;
	}
	
	@Override
	public String toString() {
		return nom;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Joueur joueur) {
			return nom == joueur.getNom();
		}
		return false;
	}
}
