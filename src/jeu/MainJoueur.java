package jeu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import cartes.Carte;

public class MainJoueur implements Iterable<Carte> {

	List<Carte> cartes = new ArrayList<>();
	
	public void prendre(Carte carte) {
		cartes.add(carte);
	}
	
	public void jouer(Carte carte) {
		if (!cartes.contains(carte)) {
			throw new IllegalArgumentException();
		}
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

	@Override
	public Iterator<Carte> iterator() {
		return new Iterateur();
	}
	
	private class Iterateur implements Iterator<Carte>{
		
		private int indiceIterateur = 0;
		
		@Override
		public boolean hasNext() {
			return indiceIterateur < cartes.size();
		}

		@Override
		public Carte next() {
			if (hasNext()) {
				return cartes.get(indiceIterateur++);
			}
			else {
				throw new NoSuchElementException();
			}
		}
		
	}
}
