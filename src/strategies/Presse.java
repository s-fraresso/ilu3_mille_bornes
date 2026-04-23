package strategies;

import java.util.NavigableSet;
import java.util.Set;

import jeu.Coup;

public interface Presse extends Strategie, Priorite {
	@Override
	public default NavigableSet<Coup> trierCoups(Set<Coup> coups){
		NavigableSet<Coup> coupsTries = new TreeSet<>();
		coupsTries.addAll(coups);
		return coupsTries;
	}
}
