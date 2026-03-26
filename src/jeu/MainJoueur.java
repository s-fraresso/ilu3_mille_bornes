package jeu;

import java.util.ArrayList;
import java.util.Iterator;
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
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		
		for (Iterator<Carte> iter = cartes.iterator(); iter.hasNext();) {
			Carte carte = iter.next();
			out.append(carte.toString());
		}
		
		return out.toString();
	}
}
