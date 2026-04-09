package jeu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import cartes.Attaque;
import cartes.Bataille;
import cartes.Borne;
import cartes.Botte;
import cartes.Carte;
import cartes.Cartes;
import cartes.DebutLimite;
import cartes.FinLimite;
import cartes.Limite;
import cartes.Parade;
import cartes.Type;

public class ZoneDeJeu {
	private List<Bataille> pileBataille = new ArrayList<>();
	private List<Limite> pileLimite = new ArrayList<>();
	private List<Borne> pileBornes = new ArrayList<>();
	private HashSet<Botte> bottes = new HashSet<>();
	
	
	public int donnerLimitationVitesse() {
		if (estPrioritaire() || pileLimite.isEmpty()) {
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
		else if (carte instanceof Bataille bataille){
			pileBataille.add(bataille);
		}
		else {
			bottes.add((Botte) carte);
		}
	}
	
	public boolean peutAvancer() {
		if (pileBataille.isEmpty()) {
			return estPrioritaire();
		}
		
		Bataille sommetBataille = pileBataille.getLast();
		if (sommetBataille instanceof Parade parade) {
			return parade.equals(Cartes.FEU_VERT) || estPrioritaire();
		}

		return estPrioritaire() && bottes.contains(new Botte(sommetBataille.getType())); // sommetBataille est forcément une attaque
	}
	
	private boolean estDepotFeuVertAutorise() {
		if (estPrioritaire()) {
			return false;
		}
		
		if (pileBataille.isEmpty()) {
			return true;
		}
		
		Bataille sommetBataille = pileBataille.getLast();
		if (sommetBataille instanceof Parade parade) {
			return !parade.equals(Cartes.FEU_VERT);
		}
		
		// sommetBataille est obligatoirement une attaque
		return sommetBataille.equals(Cartes.FEU_ROUGE) || bottes.contains(new Botte(sommetBataille.getType()));
	}
	
	private boolean estDepotBorneAutorise(Borne borne) {
		if (donnerLimitationVitesse() < borne.getKm() || donnerKmParcourus() + borne.getKm() > 1000) {
			return false;
		}
		
		Bataille sommetBataille = pileBataille.getLast();
		return sommetBataille.equals(Cartes.FEU_VERT);
	}
	
	private boolean estDepotLimiteAutorise(Limite limite) {
		if (estPrioritaire()) {
			return false;
		}
		
		if (limite instanceof DebutLimite) {
			return pileLimite.isEmpty() || pileLimite.getLast() instanceof FinLimite;
		}
		else {
			return !pileLimite.isEmpty() && pileLimite.getLast() instanceof DebutLimite;
		}
	}
	
	private boolean estDepotBatailleAutorise(Bataille bataille) {
		if (bottes.contains(new Botte(bataille.getType()))) {
			return false;
		}
		
		if (bataille instanceof Attaque) {
			return peutAvancer();
		}
		else if (bataille instanceof Parade parade) {
			if (parade.equals(Cartes.FEU_VERT)) {
				return estDepotFeuVertAutorise();
			}
			else {
				if (pileBataille.isEmpty()) {
					return false;
				}
				
				Bataille sommetBataille = pileBataille.getLast();
				return sommetBataille instanceof Attaque && sommetBataille.getType().equals(parade.getType());
			}
		}
		
		return false; // cas où bataille est une botte, à implémenter plus tard
	}
	
	public boolean estDepotAutorise(Carte carte) {
		if (carte instanceof Bataille bataille) {
			return estDepotBatailleAutorise(bataille);
		}
		
		if (carte instanceof Limite limite) {
			return estDepotLimiteAutorise(limite);
		}
		
		if (carte instanceof Borne borne) {
			return estDepotBorneAutorise(borne);
		}
		
		return true; // On peut toujours déposer une botte
	}
	
	private boolean estPrioritaire() {
		return bottes.contains(Cartes.PRIORITAIRE);
	}
}


























