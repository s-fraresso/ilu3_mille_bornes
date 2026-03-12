package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GestionCartes {
	
	static Random rng = new Random();
	
	public static <T> T extraire(List<T> liste){
		return liste.remove(rng.nextInt(liste.size()));
	}
	
	public static <T> T extraireIterateur(List<T> liste){
		int indiceExtrait = rng.nextInt(liste.size());		
		ListIterator<T> iter = liste.listIterator(indiceExtrait);
		
		T elementExtrait = iter.next();
		iter.remove();
		
		return elementExtrait;
	}
	
	public static <T> List<T> melanger(List<T> liste) {
		List<T> listeMelangee = new ArrayList<>();
		
		while (!liste.isEmpty()) {
			listeMelangee.add(GestionCartes.extraire(liste));
		}
		
		return listeMelangee;
	}
	
	public static <T> boolean verifierMelange(List<T> liste1, List<T> liste2) {
		if (liste1.size() != liste2.size()) {
			return false;
		}
		
		for (T element : liste1) {
			if (Collections.frequency(liste1, element) != Collections.frequency(liste2, element)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static <T> List<T> rassembler(List<T> liste){
		if (liste.isEmpty()) {
			return liste;
		}
		
		List<T> listeRassemblee = new ArrayList<>();
		T elementAjoute = null;
		
		while (listeRassemblee.size() != liste.size()) {
			for (T element : liste) {
				if (!listeRassemblee.contains(element)) {
					elementAjoute = element;
					break;
				}
			}
			
			for (T element : liste) {
				if (element.equals(elementAjoute)){
					listeRassemblee.add(element);
				}
			}
		}
		
		return listeRassemblee;
	}
	
	public static <T> boolean verifierRassemblement(List<T> liste) {
		if (liste.isEmpty()) {
			return true;
		}
		
		T elementCourant = liste.get(0);
		
		for (ListIterator<T> iter1 = liste.listIterator(); iter1.hasNext();) {
			T elementSuivant = iter1.next();
			
			if (!elementSuivant.equals(elementCourant)) {
				for (ListIterator<T> iter2 = liste.listIterator(iter1.nextIndex()); iter2.hasNext();) {
					if (iter2.next().equals(elementCourant)){
						return false;
					}
				}
				
				elementCourant = elementSuivant;
			}
		}
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
}
