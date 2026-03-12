package utils;

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
}
