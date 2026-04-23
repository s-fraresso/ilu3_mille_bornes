package jeu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import cartes.Botte;
import cartes.Carte;

public class Joueur {
	private String nom;
	private ZoneDeJeu zoneDeJeu;
	private MainJoueur main = new MainJoueur();
	private Random random = new Random();
	
	public Joueur(String nom, ZoneDeJeu zoneDeJeu) {
		this.nom = nom;
		this.zoneDeJeu = zoneDeJeu;
	}
	
	public void donner(Carte carte) {
		main.prendre(carte);
	}
	
	public Carte prendreCarte(Sabot sabot) {
		if (sabot.estVide()) {
			return null;
		}
		
		Carte carte = sabot.piocher();
		donner(carte);
		return carte;
	}
	
	public int donnerKmParcourus() {
		return zoneDeJeu.donnerKmParcourus();
	}
	
	public void deposer(Carte carte) {
		zoneDeJeu.deposer(carte);
	}
	
	public Set<Coup> coupsPossibles(Set<Joueur> participants) {
		HashSet<Coup> coups = new HashSet<>();
		
		for (Joueur joueurCible : participants) {
			for (Iterator<Carte> iter = main.iterator(); iter.hasNext();) {
				Carte carte = iter.next();	
				Coup coup = new Coup(this, carte, joueurCible);
				
				if (coup.estValide()) {
					coups.add(coup);
				}
			}
		}
		
		return coups;
	}
	
	public Set<Coup> coupsDefausse() {
		HashSet<Coup> coups = new HashSet<>();
		
		for (Iterator<Carte> iter = main.iterator(); iter.hasNext();) {
			Carte carte = iter.next();
			coups.add(new Coup(this, carte, null));
		}
		
		return coups;
	}
	
	public void retirerDeLaMain(Carte carte) {
		main.jouer(carte);
	}
	
	public Coup choisirCoup(Set<Joueur> participants) {
		Set<Coup> coups = coupsPossibles(participants);
		if (coups.isEmpty()) {
			coups = coupsDefausse();
		}
		
		Coup[] coupsArr = coups.toArray(new Coup[coups.size()]);
		return coupsArr[random.nextInt(coupsArr.length)];
	}
	
	public String afficherEtatJoueur() {
		StringBuilder out = new StringBuilder("Joueur : " + nom);
		out.append("\nBottes : ");
		for (Botte botte : zoneDeJeu.getBottes()) {
			out.append(botte.toString()).append(" ");
		}
		
		out.append("\nLimitation de vitesse : " + (zoneDeJeu.donnerLimitationVitesse() != 200));
		if (zoneDeJeu.getPileBataille().isEmpty()) {
			out.append("\nSommet de la pile de Bataille : null");
		}
		else {
			out.append("\nSommet de la pile de Bataille : " + zoneDeJeu.getPileBataille().getFirst());
		}
		
		out.append("\nContenu de la main : ");
		for (Iterator<Carte> iter = main.iterator(); iter.hasNext();) {
			Carte carte = iter.next();
			out.append(carte.toString());
			if (iter.hasNext()) {
				out.append(", ");
			}
		}
		out.append("\nKm parcourus : " + zoneDeJeu.donnerKmParcourus());
		out.append("\n");
		
		return out.toString();
	}
	
	public String getNom() {
		return nom;
	}
	
	public MainJoueur getMain() {
		return main;
	}
	
	public ZoneDeJeu getZoneDeJeu() {
		return zoneDeJeu;
	}
	
	@Override
	public String toString() {
		return nom;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Joueur joueur) {
			return nom.equals(joueur.getNom());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 19 * nom.hashCode();
	}
}
