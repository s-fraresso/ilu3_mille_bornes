package cartes;

public class JeuDeCartes {
	private Configuration[] typesDeCartes = {
			new Configuration(new Borne(25), 10),
			new Configuration(new Borne(50), 10),
			new Configuration(new Borne(75), 10),
			new Configuration(new Borne(100), 12),
			new Configuration(new Borne(200), 4),
			
			new Configuration(new Parade(Type.FEU), 14),
			new Configuration(new Parade(Type.ESSENCE), 6),
			new Configuration(new Parade(Type.CREVAISON), 6),
			new Configuration(new Parade(Type.ACCIDENT), 6),
			
			new Configuration(new Attaque(Type.FEU), 5),
			new Configuration(new Attaque(Type.ESSENCE), 3),
			new Configuration(new Attaque(Type.CREVAISON), 3),
			new Configuration(new Attaque(Type.ACCIDENT), 3),
			
			new Configuration(new Botte(Type.FEU), 1),
			new Configuration(new Botte(Type.ESSENCE), 1),
			new Configuration(new Botte(Type.CREVAISON), 1),
			new Configuration(new Botte(Type.ACCIDENT), 1),
			
			new Configuration(new DebutLimite(), 4),
			new Configuration(new FinLimite(), 6),
	};
	
	public String affichageJeuDeCartes() {
		StringBuilder out = new StringBuilder();
		
		for (Configuration configuration : typesDeCartes) {
			out.append(configuration.getNbExemplaires());
			out.append(" ");
			out.append(configuration.getCarte().toString());
			out.append("\n");
		}
		return out.toString();
	}
	
	public Carte[] donnerCartes() {
		Carte[] cartes = new Carte[106];
		int carteIndex = 0;
		
		for (Configuration configuration : typesDeCartes) {
			for (int i = 0; i < configuration.getNbExemplaires(); i++) {
				cartes[carteIndex++] = configuration.getCarte();
			}
		}
		
		return cartes;
	}
	
	public boolean checkCount() {
		Carte[] cartes = donnerCartes();
		
		for (Configuration configuration : typesDeCartes) {
			if (!checkConfiguration(configuration, cartes)){
				return false;
			}
		}
	
		return true;
	}
	
	private boolean checkConfiguration(Configuration configuration, Carte[] cartes) {
		int nbTrouvees = 0;
		
		for (int i = 0; i < cartes.length; i++) {
			if (cartes[i].equals(configuration.getCarte())) {
				nbTrouvees++;
			}
		}
		
		return nbTrouvees == configuration.getNbExemplaires();
	}
	
	private static class Configuration {
		private Carte carte;
		private int nbExemplaires;
		
		private Configuration(Carte carte, int nbExemplaires) {
			this.carte = carte;
			this.nbExemplaires = nbExemplaires;
		}

		public Carte getCarte() {
			return carte;
		}

		public int getNbExemplaires() {
			return nbExemplaires;
		}
	}
}
