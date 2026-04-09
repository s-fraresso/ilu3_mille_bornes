package jeu;

import cartes.Attaque;
import cartes.Carte;
import cartes.DebutLimite;

public class Coup {
	private Joueur joueurCourant;
	private Carte carteJouee;
	private Joueur joueurCible;
	
	public Coup(Joueur joueurCourant, Carte carteJouee, Joueur joueurCible) {
		super();
		this.joueurCourant = joueurCourant;
		this.carteJouee = carteJouee;
		this.joueurCible = joueurCible;
	}

	public Joueur getJoueurCourant() {
		return joueurCourant;
	}

	public Carte getCarteJouee() {
		return carteJouee;
	}

	public Joueur getJoueurCible() {
		return joueurCible;
	}
	
	public boolean estValide() {
		if (carteJouee instanceof Attaque || carteJouee instanceof DebutLimite) {
			return !joueurCourant.equals(joueurCible);
		}
		
		return joueurCourant.equals(joueurCible);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Coup coup) {
			return joueurCourant.equals(coup.getJoueurCourant()) &&
					carteJouee.equals(coup.getCarteJouee()) &&
					joueurCible.equals(coup.getJoueurCible());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 23 * joueurCourant.hashCode() * carteJouee.hashCode() * joueurCible.hashCode();
	}
	
	@Override
	public String toString() {
		if (joueurCible == null) {
			return "defausse la carte " + carteJouee.toString();
		}
		return "depose la carte " + carteJouee.toString() + "dans la zone de jeu de " + joueurCible.getNom();

	}
}
