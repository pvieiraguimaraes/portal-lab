package br.ueg.portalLab.model.jogo.cruzada;

public class Square {
	String value = "A";
	Boolean visibility = false;
	Boolean fixed = false;
	Answer horizontalAnswer = null;
	Answer verticalAnswer = null;
	Object squareView = null;
	String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Boolean getVisibility() {
		return visibility;
	}
	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}
	public Boolean getFixed() {
		return fixed;
	}
	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}
	public Answer getHorizontalAnswer() {
		return horizontalAnswer;
	}
	public void setHorizontalAnswer(Answer horizontalAnswer) {
		this.horizontalAnswer = horizontalAnswer;
	}
	public Answer getVerticalAnswer() {
		return verticalAnswer;
	}
	public void setVerticalAnswer(Answer verticalAnswer) {
		this.verticalAnswer = verticalAnswer;
	}
	public Object getSquareView() {
		return squareView;
	}
	public void setSquareView(Object squareView) {
		this.squareView = squareView;
	}
	
	

	
}
