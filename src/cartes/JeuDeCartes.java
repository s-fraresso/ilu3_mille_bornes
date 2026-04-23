package cartes;

import java.util.HashMap;
import java.util.Map;

public class JeuDeCartes {
	private Map<Carte, Integer> typesDeCartes = new HashMap<>();
	
	public JeuDeCartes() {
		typesDeCartes.put(new Borne(25), 10);
		typesDeCartes.put(new Borne(50), 10);
		typesDeCartes.put(new Borne(75), 10);
		typesDeCartes.put(new Borne(100), 12);
		typesDeCartes.put(new Borne(200), 4);
		
		typesDeCartes.put(new Parade(Type.FEU), 14);
		typesDeCartes.put(new Parade(Type.ESSENCE), 6);
		typesDeCartes.put(new Parade(Type.CREVAISON), 6);
		typesDeCartes.put(new Parade(Type.ACCIDENT), 6);
		
		typesDeCartes.put(new Attaque(Type.FEU), 5);
		typesDeCartes.put(new Attaque(Type.ESSENCE), 3);
		typesDeCartes.put(new Attaque(Type.CREVAISON), 3);
		typesDeCartes.put(new Attaque(Type.ACCIDENT), 3);
		
		typesDeCartes.put(new Botte(Type.FEU), 1);
		typesDeCartes.put(new Botte(Type.ESSENCE), 1);
		typesDeCartes.put(new Botte(Type.CREVAISON), 1);
		typesDeCartes.put(new Botte(Type.ACCIDENT), 1);
		
		typesDeCartes.put(new DebutLimite(), 4);
		typesDeCartes.put(new FinLimite(), 6);
	}
	
	public String affichageJeuDeCartes() {
		StringBuilder out = new StringBuilder();
		
		for (Map.Entry<Carte, Integer> configuration : typesDeCartes.entrySet()) {
			Carte key = configuration.getKey();
			Integer val = configuration.getValue();
			
			out.append(val);
			out.append(" ");
			out.append(key.toString());
			out.append("\n");
		}
		
		return out.toString();
	}
	
	public Carte[] donnerCartes() {
		Carte[] cartes = new Carte[106];
		int carteIndex = 0;
		
		for (Map.Entry<Carte, Integer> configuration : typesDeCartes.entrySet()) {
			Carte key = configuration.getKey();
			Integer val = configuration.getValue();
			
			for (int i = 0; i < val; i++) {
				cartes[carteIndex++] = key;
			}
		}
		
		return cartes;
	}
	
	public boolean checkCount() {
		Carte[] cartes = donnerCartes();
		
		for (Map.Entry<Carte, Integer> configuration : typesDeCartes.entrySet()) {
			if (!checkConfiguration(configuration, cartes)){
				return false;
			}
		}
	
		return true;
	}
	
	private boolean checkConfiguration(Map.Entry<Carte, Integer> configuration, Carte[] cartes) {
		int nbTrouvees = 0;
		
		for (int i = 0; i < cartes.length; i++) {
			if (cartes[i].equals(configuration.getKey())) {
				nbTrouvees++;
			}
		}
		
		return nbTrouvees == configuration.getValue();
	}
}
