package cartes;

public enum Type {
	FEU("Feu Rouge", "Feu vert", "Véhicule prioritaire"), 
	ESSENCE("Panne d'essence", "Essence", "Citerne d'essence"), 
	CREVAISON("Crevaison", "Roue de secours", "Increvable"), 
	ACCIDENT("Accident", "Réparations", "As du volant");
	
	private String texteAttaque;
	private String texteParade;
	private String texteBotte;
	
	private Type(String texteAttaque, String texteParade, String texteBotte) {
		this.texteAttaque = texteAttaque;
		this.texteParade = texteParade;
		this.texteBotte = texteBotte;
	}

	public String getTexteAttaque() {
		return texteAttaque;
	}

	public String getTexteParade() {
		return texteParade;
	}

	public String getTexteBotte() {
		return texteBotte;
	}
	
}
