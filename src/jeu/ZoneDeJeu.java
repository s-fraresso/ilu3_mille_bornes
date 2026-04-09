package jeu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

public class ZoneDeJeu {
	private List<Bataille> pileBataille = new LinkedList<>();
	private List<Limite> pileLimite = new LinkedList<>();
	private List<Borne> pileBornes = new LinkedList<>();
	private HashSet<Botte> bottes = new HashSet<>();
	
	
	public int donnerLimitationVitesse() {
		if (estPrioritaire() || pileLimite.isEmpty()) {
			return 200;
		}
		
		Limite sommetLimite = pileLimite.getFirst();
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
		switch (carte) {
			case Borne borne:
				pileBornes.addFirst(borne);
				break;
			case Limite limite:
				pileLimite.addFirst(limite);
				break;
			case Bataille bataille:
				pileBataille.addFirst(bataille);
  				break;
  			default:
  				bottes.add((Botte) carte);
		}
	}
	
	public boolean peutAvancer() {
		if (pileBataille.isEmpty()) {
			return estPrioritaire();
		}
		
		Bataille sommetBataille = pileBataille.getFirst();
		if (sommetBataille instanceof Parade parade) {
			return parade.equals(Cartes.FEU_VERT) || estPrioritaire();
		}

		// sommetBataille est forcément une attaque
		return estPrioritaire() && bottes.contains(new Botte(sommetBataille.getType())); 
	}
	
	private boolean estDepotFeuVertAutorise() {
		if (estPrioritaire()) {
			return false;
		}
		
		if (pileBataille.isEmpty()) {
			return true;
		}
		
		Bataille sommetBataille = pileBataille.getFirst();
		if (sommetBataille instanceof Parade parade) {
			return !parade.equals(Cartes.FEU_VERT);
		}
		
		// sommetBataille est obligatoirement une attaque
		return sommetBataille.equals(Cartes.FEU_ROUGE) || bottes.contains(new Botte(sommetBataille.getType()));
	}
	
	private boolean estDepotBorneAutorise(Borne borne) {
		if (donnerLimitationVitesse() < borne.getKm()
				||donnerKmParcourus() + borne.getKm() > 1000
				|| pileBataille.isEmpty()) {
			return false;
		}
		
		Bataille sommetBataille = pileBataille.getFirst();
		return sommetBataille.equals(Cartes.FEU_VERT);
	}
	
	private boolean estDepotLimiteAutorise(Limite limite) {
		if (estPrioritaire()) {
			return false;
		}
		
		if (limite instanceof DebutLimite) {
			return pileLimite.isEmpty() || pileLimite.getFirst() instanceof FinLimite;
		}
		else {
			return !pileLimite.isEmpty() && pileLimite.getFirst() instanceof DebutLimite;
		}
	}
	
	private boolean estDepotBatailleAutorise(Bataille bataille) {
		if (bottes.contains(new Botte(bataille.getType()))) {
			return false;
		}
		
		if (bataille instanceof Parade parade) {
			if (parade.equals(Cartes.FEU_VERT)) {
				return estDepotFeuVertAutorise();
			}
			else {
				if (pileBataille.isEmpty()) {
					return false;
				}
				
				Bataille sommetBataille = pileBataille.getFirst();
				return sommetBataille instanceof Attaque && sommetBataille.getType().equals(parade.getType());
			}
		}
		
		return peutAvancer(); // bataille est une attaque
	}
	
	public boolean estDepotAutorise(Carte carte) {
		switch (carte) {
			case Borne borne:
				return estDepotBorneAutorise(borne);
			case Limite limite:
				return estDepotLimiteAutorise(limite);
			case Bataille bataille:
				return estDepotBatailleAutorise(bataille);
			default:
				return true; // On peut toujours déposer une botte
		}
	}
	
	private boolean estPrioritaire() {
		return bottes.contains(Cartes.PRIORITAIRE);
	}
	
	public Set<Botte> getBottes() {
		return bottes;
	}
	
	public List<Bataille> getPileBataille() {
		return pileBataille;
	}
}


























