package br.ueg.portalLab.model.jogo.cruzada;

public enum TypeAnswer {

	VERTICAL("Vertical"),
	HORIZONTAL("Horizontal");
	
	private String name;
	TypeAnswer(String name)
	{
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
