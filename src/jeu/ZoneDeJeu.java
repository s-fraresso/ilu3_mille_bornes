package jeu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cartes.Bataille;
import cartes.Borne;
import cartes.Carte;
import cartes.Cartes;
import cartes.FinLimite;
import cartes.Limite;
import cartes.Parade;

public class ZoneDeJeu {
	private List<Bataille> pileBataille = new ArrayList<>();
	private List<Limite> pileLimite = new ArrayList<>();
	private List<Borne> pileBornes = new ArrayList<>();
	
	
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
	
	public int donnerKmParcourus() {
		int km = 0;
		
		for(Iterator<Borne> iter = pileBornes.iterator(); iter.hasNext();) {
			Borne borne = iter.next();
			km += borne.getKm();
		}
		
		return km;
	}
	
	public void deposer(Carte carte) {
		if (carte instanceof Borne borne) {
			pileBornes.add(borne);
		}
		else if (carte instanceof Limite limite) {
			pileLimite.add(limite);
		}
		else {
			pileBataille.add((Bataille) carte);
		}
	}
	
	public boolean peutAvancer() {
		if (pileBataille.isEmpty()) {
			return false;
		}
		
		Bataille sommetBataille = pileBataille.getLast();
		return sommetBataille.equals(Cartes.FEU_VERT);
	}
	
	public boolean estDepotFeuVertAutorise() {
		if (pileBataille.isEmpty()) {
			return true;
		}
		
		Bataille sommetBataille = pileBataille.getLast();
		if (sommetBataille instanceof Parade parade) {
			return !parade.equals(Cartes.FEU_VERT);
		}
		
		return sommetBataille.equals(Cartes.FEU_ROUGE);
	}
	
	public boolean estDepotBorneAutorise(Borne borne) {
		if (donnerLimitationVitesse() < borne.getKm() || donnerKmParcourus() + borne.getKm() > 1000) {
			return false;
		}
		
		Bataille sommetBataille = pileBataille.getLast();
		return sommetBataille.equals(Cartes.FEU_VERT);
	}
}


























