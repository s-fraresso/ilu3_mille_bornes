package testsFonctionnels;

import cartes.Attaque;
import cartes.Borne;
import cartes.Type;
import cartes.Carte;
import cartes.Parade;


public class TestMethodeEquals {
	
	public static void main(String[] args) {
		Carte borne1 = new Borne(25);
		Carte borne2 = new Borne(25);
		
		Carte feuRouge1 = new Attaque(Type.FEU);
		Carte feuRouge2 = new Attaque(Type.FEU);
		
		Carte feuVert = new Parade(Type.FEU);
		
		System.out.println("Deux cartes de 25km sont identiques ?" + borne1.equals(borne2));
		System.out.println("Deux cartes de feux rouge sont identiques ?" + feuRouge1.equals(feuRouge2));
		System.out.println("La carte feu rouge et la carte feu vert sont identiques ?" + feuRouge1.equals(feuVert));
	}
}
