package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import cartes.Carte;
import cartes.JeuDeCartes;
import utils.GestionCartes;

public class Jeu {
	private final static int NBCARTES = 6;
	
	private Sabot sabot;
	private LinkedHashSet<Joueur> joueurs;
	
	public void inscrire(Joueur ... joueursInscrits) {
		for (Joueur joueur : joueursInscrits) {
			joueurs.add(joueur);
		}
	}
	
	public void distribuerCartes() {
		for (int i = 0; i < NBCARTES; i++) {
			for (Joueur joueur : joueurs) {
				joueur.donner(sabot.piocher());
			}
		}
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
