package strategies;

import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import cartes.Attaque;
import cartes.Botte;
import cartes.Carte;
import jeu.Coup;
import jeu.Joueur;

public interface Presse extends Strategie, Priorite {
	Random RANDOM = new Random();


	private int comparerCartes(Joueur joueur, Carte carte1, Carte carte2) {			
		Integer comparaison = null;
		
		comparaison = donnerPrioriteLimites(carte1, carte2);
		if(comparaison != null) {
			return comparaison;
		}			
		
		comparaison = donnerPrioriteBornes(carte1, carte2);
		if(comparaison != null) {
			return comparaison;
		}
		
		Carte carteSommet = joueur.donnerSommetPile();
		if(carteSommet instanceof Attaque attaque) {

			Type typeProbleme = attaque.getType();
			if(joueur.donnerBottes().contains(new Botte(typeProbleme))) {
				typeProbleme = Type.FEU;
			}
			
			comparaison = donnerPrioriteBottes(typeProbleme, carte1, carte2);
			if(comparaison != null) {
				return comparaison;
			}					
		}
		
		comparaison = donnerPrioriteParades(carte1, carte2);
		if(comparaison != null) {
			return comparaison;
		}
		if (RANDOM.nextBoolean()) {
			return 1;
		} else {
			return -1;
		}
	}

	
	@Override
	public default NavigableSet<Coup> trierCoups(Set<Coup> coups){
		NavigableSet<Coup> coupsTries = new TreeSet<>();
		coupsTries.addAll(coups);
		return coupsTries;
	}
}
