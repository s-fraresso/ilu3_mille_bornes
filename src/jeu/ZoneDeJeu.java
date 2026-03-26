package jeu;

import java.util.List;

import cartes.Bataille;
import cartes.Borne;
import cartes.FinLimite;
import cartes.Limite;

public class ZoneDeJeu {
	private List<Bataille> pileBataille;
	private List<Limite> pileLimite;
	private List<Borne> pileBornes;
	
	
	public int donnerLimitationVitesse() {
		if (pileLimite.isEmpty()) {
			return 200;
		}
		
		Limite sommetLimite = pileLimite.getLast();
		if (sommetLimite instanceof FinLimite) {
			return 200;
		}
		
		return 50;
	}
}
