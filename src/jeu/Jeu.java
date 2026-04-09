package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import cartes.Carte;
import cartes.JeuDeCartes;
import utils.GestionCartes;

public class Jeu {
	private static final int NBCARTES = 6;
	
	private Sabot sabot;
	private LinkedHashSet<Joueur> joueurs = new LinkedHashSet<>();
	
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
	
	public String jouerTour(Joueur joueur) {
		StringBuilder out = new StringBuilder();
		Carte cartePiochee = sabot.piocher();
		joueur.donner(cartePiochee);
		out.append("Le joueur " + joueur.getNom() + "a pioche " + cartePiochee.toString());
		
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
		out.append(joueur.getNom() + coup.toString());
		
		return out.toString();
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
