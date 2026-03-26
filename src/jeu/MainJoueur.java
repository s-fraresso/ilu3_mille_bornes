package jeu;

import java.util.ArrayList;
import java.util.List;

import cartes.Carte;

public class MainJoueur {

	List<Carte> cartes = new ArrayList<>();
	
	public void prendre(Carte carte) {
		cartes.add(carte);
	}
	
	public void jouer(Carte carte) {
		assert cartes.contains(carte);
		cartes.remove(carte);
	}
}
