package jeu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import cartes.Botte;
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
	
	public HashSet<Coup> coupsPossibles(Set<Joueur> participants) {
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
	
	public HashSet<Coup> coupsDefausse() {
		HashSet<Coup> coups = new HashSet<>();
		
		for (Iterator<Carte> iter = main.iterator(); iter.hasNext();) {
			Carte carte = iter.next();
			coups.add(new Coup(this, carte, null));
		}
		
		return coups;
	}
	
	public void retirerDeLaMain(Carte carte) {
		main.jouer(carte);
	}
	
	public Coup choisirCoup(Set<Joueur> participants) {
		Random rng = new Random();
		
		HashSet<Coup> coups = coupsPossibles(participants);
		if (coups.isEmpty()) {
			coups = coupsDefausse();
		}
		
		Coup coupsArr[] = coups.toArray(new Coup[coups.size()]);
		return coupsArr[rng.nextInt(coupsArr.length)];
	}
	
	String afficherEtatJoueur() {
		StringBuilder out = new StringBuilder("Bottes :\n");
		for (Botte botte : zoneDeJeu.getBottes()) {
			out.append(botte.toString());
		}
		
		out.append("\nLimitation de vitesse : " + (zoneDeJeu.donnerLimitationVitesse() != 200));
		if (zoneDeJeu.getPileBataille().isEmpty()) {
			out.append("\nSommet de la pile de Bataille : null");
		}
		else {
			out.append("\nSommet de la pile de Bataille : " + zoneDeJeu.getPileBataille().getLast());
		}
		
		out.append("Contenu de la main :\n");
		for (Iterator<Carte> iter = main.iterator(); iter.hasNext();) {
			Carte carte = iter.next();
			out.append(carte.toString());
		}
		
		return out.toString();
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
