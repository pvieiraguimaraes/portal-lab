package br.ueg.portalLab.view.component.jogos.cruzadas;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Vlayout;

import br.ueg.portalLab.model.jogo.cruzada.CrossWord;
import br.ueg.portalLab.model.jogo.cruzada.Square;

// @org.springframework.stereotype.Component
// @Scope("desktop")
@SuppressWarnings("serial")
public class ZKCrossWordComponent extends Vlayout {

	public static final String CONTAINER_ID = "crosswordContainer";
	public static final String FORM_ID = "crosswordForm";
	private CrossWord crossword;
    private ZKCrossWord crosswordController;
    private ZKCrossWordForm crosswordForm;
	private boolean admMode = false;
	private String squareClickEvent;
	private Square clickedSquare;
	
	
	
	public CrossWord getCrossword() {
		return crossword;
	}

	public void setCrossword(CrossWord crossword) {
		this.crossword = crossword;
	}

	
	/*
	 * 
	@Deprecated
	public void onCreate$crosswordContainer(Event e) {
		CrossWord crossword = (CrossWord) getAttribute("crossword");
		if (crossword != null) {
			loadCrossWord(crossword);
		}
	}
	*/

	public Vlayout getCrossCrontainer() {
		Vlayout crosswordContainer = (Vlayout) getFellowIfAny(CONTAINER_ID);
		if (crosswordContainer == null) {
			crosswordContainer = new Vlayout();
			crosswordContainer.setId(CONTAINER_ID);
			crosswordContainer.setSclass(getSclass());
			appendChild(crosswordContainer);
		}
		return crosswordContainer;
	}

	public void loadCrossWord()
	{
		loadCrossWord(getCrossword());
	}
	public void loadCrossWord(CrossWord crossword) {
		if(crossword != null && crossword.getAnswers() != null && !crossword.getAnswers().isEmpty()
			&& crossword.getCrossXDimension() > 0 && crossword.getCrossYDimension() > 0) 	
		{
			this.crosswordController = new ZKCrossWord();
			Vlayout croswordContainer = getCrossCrontainer();
			this.crosswordController.setCrossComponent(this);
			this.crosswordController.setActualContainer(croswordContainer);
			this.crosswordForm = new ZKCrossWordForm();
			this.crosswordController.setCrossWordForm(this.crosswordForm);
			this.crosswordController.cronstructCrossWord(crossword, croswordContainer);
		
			Vlayout croswordFormContainer = getCrossFormCrontainer();
			this.crosswordForm.setActualContainer(croswordFormContainer);
			this.crosswordForm.cronstructForm(crossword.getAnswers(), croswordFormContainer);
		}	
		/*
		///alterar este método chamando a cruzadinha
		this.actualContainer = croswordContainer;
		cronstructCrossWord(crossword, croswordContainer);
		///alterar este método
		 * 
		 */

	}

   
	private Vlayout getCrossFormCrontainer() {
		Vlayout crosswordFormContainer = (Vlayout) getFellowIfAny(FORM_ID);
		if (crosswordFormContainer == null) {
			crosswordFormContainer = new Vlayout();
			crosswordFormContainer.setId(FORM_ID);
			crosswordFormContainer.setSclass(getSclass());
			appendChild(crosswordFormContainer);
		}
		return crosswordFormContainer;
	}

	public void submitCrossWord() {
	  if (crosswordController.submitCrossWord())
		  Messagebox.show("Parabéns Voce venceu");
	}

	public String getSquareClickEvent() {
		return squareClickEvent;
	}

	public void setSquareClickEvent(String squareClickEvent) {
		this.squareClickEvent = squareClickEvent;
	}

	public boolean isAdmMode() {
		return admMode;
	}

	public void setAdmMode(boolean admMode) {
		this.admMode = admMode;
	}

	public Square getClickedSquare() {
		return clickedSquare;
	}

	public void setClickedSquare(Square clickedSquare) {
		this.clickedSquare = clickedSquare;
	}
	
	public void setClickedSquare(String clickedSquare) {
		this.clickedSquare = getCrossword().getSquares().get(clickedSquare);
	}
	
}

