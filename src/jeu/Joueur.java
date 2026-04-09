package jeu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cartes.Carte;

public class Joueur {
	private String nom;
	private ZoneDeJeu zoneDeJeu;
	private MainJoueur main = new MainJoueur();
	
	public Joueur(String nom, ZoneDeJeu zoneDeJeu) {
		this.nom = nom;
		this.zoneDeJeu = zoneDeJeu;
	}
	
	public void donner(Carte carte) {
		main.prendre(carte);
	}
	
	public Carte prendreCarte(Sabot sabot) {
		if (sabot.estVide()) {
			return null;
		}
		
		Carte carte = sabot.piocher();
		donner(carte);
		return carte;
	}
	
	public int donnerKmParcourus() {
		return zoneDeJeu.donnerKmParcourus();
	}
	
	public void deposer(Carte carte) {
		zoneDeJeu.deposer(carte);
	}
	
	public HashSet<Coup> coupsPossibles(Set<Joueur> participants){
		HashSet<Coup> coups = new HashSet<>();
		
		for (Joueur joueurCourant : participants) {
			MainJoueur main = joueurCourant.getMain();
			for (Iterator<Carte> iter = main.iterator(); iter.hasNext();) {
				Carte carte = iter.next();
				
				for (Joueur joueurCible : participants) {
					Coup coup = new Coup(joueurCourant, carte, joueurCible);
					if (coup.estValide()) {
						coups.add(coup);
					}
				}
			}
		}
		
		return coups;
	}
	
	public String getNom() {
		return nom;
	}
	
	public MainJoueur getMain() {
		return main;
	}
	
	@Override
	public String toString() {
		return nom;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Joueur joueur) {
			return nom.equals(joueur.getNom());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 19 * nom.hashCode();
	}
}
