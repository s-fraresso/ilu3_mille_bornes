package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import cartes.Carte;
import cartes.JeuDeCartes;
import utils.GestionCartes;

public class Jeu {
	private static final int NBCARTES = 6;
	
	private Sabot sabot;
	private LinkedHashSet<Joueur> joueurs = new LinkedHashSet<>();
	private Iterator<Joueur> iterJoueurs;
	
	public void inscrire(Joueur ... joueursInscrits) {
		for (Joueur joueur : joueursInscrits) {
			joueurs.addLast(joueur);
		}
	}
	
	public void distribuerCartes() {
		for (int i = 0; i < NBCARTES; i++) {
			for (Joueur joueur : joueurs) {
				joueur.donner(sabot.piocher());
			}
		}
	}
	
	public Joueur donnerJoueurSuivant() {
		if (joueurs.isEmpty()) {
			throw new IllegalStateException("Aucun joueur");
		}
		if (iterJoueurs == null || !iterJoueurs.hasNext()) {
			iterJoueurs = joueurs.iterator();
		}
		return iterJoueurs.next();
	}
	
	public String jouerTour(Joueur joueur) {
		StringBuilder out = new StringBuilder();
		Carte cartePiochee = sabot.piocher();
		joueur.donner(cartePiochee);
		out.append("Le joueur " + joueur.getNom() + " a pioche " + cartePiochee.toString() + "\n");
		
		out.append("Il a dans sa main : [");
		for (Iterator<Carte> iter = joueur.getMain().iterator(); iter.hasNext();) {
			Carte carte = iter.next();
			out.append(carte.toString());
			if (iter.hasNext()) {
				out.append(", ");
			}
		}
		out.append("]\n");
		
		Coup coup = joueur.choisirCoup(joueurs);
		Carte carteJouee = coup.getCarteJouee();
		joueur.retirerDeLaMain(carteJouee);
		if (coup.getJoueurCible() == null) {
			sabot.ajouterCarte(carteJouee);
		}
		else {
			coup.getJoueurCible().deposer(carteJouee);
		}
		out.append(joueur.getNom() + " " + coup.toString() + "\n");
		
		return out.toString();
	}
	
	public String lancer() {
		StringBuilder out = new StringBuilder("Debut : \n\n");
		
		while (!sabot.estVide()) {
			Joueur joueurCourant = donnerJoueurSuivant();
			out.append(joueurCourant.afficherEtatJoueur());
			out.append(jouerTour(joueurCourant));
			out.append(donnerClassement());
			out.append("\n");
			
			int km = joueurCourant.donnerKmParcourus();
			if (km >= 1000) {
				out.append(joueurCourant.getNom()).append(" a parcouru ").append(Integer.toString(km));
				out.append(" km. Il remporte la partie.\n");
				return out.toString();
			}
		}
		
		out.append("Le sabot est épuisé, la partie est terminée.\n");
		out.append(donnerClassement());
		return out.toString();
	}
	
	private String donnerClassement() {
		List<Joueur> listeJoueursDecroissant = classement();
		int place = 1;
		
		StringBuilder out = new StringBuilder("Le classement des joueurs est : \n");
		for (Iterator<Joueur> it = listeJoueursDecroissant.iterator(); it.hasNext(); place++) {
			Joueur joueur = it.next();
			out.append(place + " - " + joueur + " a parcouru " + joueur.donnerKmParcourus() + "\n");
		}
		
		return out.toString();
	}
	
	private List<Joueur> classement() {
		NavigableSet<Joueur> joueursClasses =
			new TreeSet<>(
				new Comparator<Joueur>() {
					@Override
					public int compare(Joueur joueur1, Joueur joueur2) {
						int compareKm = joueur1.donnerKmParcourus() - joueur2.donnerKmParcourus();
						if (compareKm == 0) {
							return joueur1.getNom().compareTo(joueur2.getNom());
						}
						
						return compareKm;
					}
				}
			);
		joueursClasses.addAll(joueurs);
		
		List<Joueur> listeJoueursDecroissant = new ArrayList<>();
		listeJoueursDecroissant.addAll(joueursClasses.descendingSet());
		return listeJoueursDecroissant;
	}
	
	public Jeu() {
		JeuDeCartes jeuDeCartes = new JeuDeCartes();
		Carte[] cartes = jeuDeCartes.donnerCartes();
		List<Carte> listeCartes = new ArrayList<>();
		Collections.addAll(listeCartes, cartes);
		listeCartes = GestionCartes.melanger(listeCartes);
		sabot = new Sabot(listeCartes.toArray(new Carte[0]));
	}
}
