package br.ueg.portalLab.view.component.jogos.cruzadas;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vlayout;

import br.ueg.portalLab.model.jogo.cruzada.Answer;
import br.ueg.portalLab.model.jogo.cruzada.CrossWord;
import br.ueg.portalLab.model.jogo.cruzada.Square;
import br.ueg.portalLab.model.jogo.cruzada.TypeAnswer;

// @org.springframework.stereotype.Component
// @Scope("desktop")
public class ZKCrossWord {

	private CrossWordLogical gameLogical = new CrossWordLogical();
	private Div divSelected;
	private String divSelectedValue;
	private TypeAnswer directionalAnswerSelected;
	private Vlayout actualContainer;
	private ZKSquareComponentController squareController;
	private ZKCrossWordForm crosswordForm;
	private ZKCrossWordComponent crossComponent;

	public void setCrossComponent(ZKCrossWordComponent crossComponent) {
		this.crossComponent = crossComponent;
	}

	public void cronstructCrossWord(CrossWord crossword,
			Vlayout crosswordContainer) {
		crosswordContainer.getChildren().clear();
		gameLogical.newGame(crossword);
		gameLogical.mountSquares();
		squareController = new ZKSquareComponentController(this);
		squareController.setCrosswordForm(this.crosswordForm);
		if (crossComponent.isAdmMode()) {
			mountAdmCrossWord(crossword, crosswordContainer);
		} else {
			mountUserCrossWord(crossword, crosswordContainer);
		}
		crossComponent.setCrossword(crossword);
	}

	private void mountAdmCrossWord(final CrossWord crossword,
			Vlayout crosswordContainer) {
		for (int y = 1; y <= crossword.getCrossYDimension(); y++) {
			Hlayout crossLine = new Hlayout();

			for (int x = 1; x <= crossword.getCrossXDimension(); x++) {

				final Div viewSquare = new Div();
				Label viewSquareText = new Label();
				final String formatedPosition = x + "." + y;
				Square square = crossword.getSquares().get(formatedPosition);
				viewSquare.setId(formatedPosition);
				if (square == null) {
					square = new Square();
					square.setId(formatedPosition);
					crossword.getSquares().put(formatedPosition, square);
					viewSquareText.setValue("");
				} else {
						viewSquareText.setValue(square.getValue());
					}

				/*
				 * viewSquare.addEventListener("onClick", new
				 * EventListener<Event>() { public void onEvent(Event event)
				 * throws Exception {
				 * crossComponent.setClickedSquare(crossword.getSquares
				 * ().get(formatedPosition));
				 * //viewSquare.addEventHandler("onClick", new EventHandler(new
				 * ZScript("java",crossComponent.getSquareClickEvent()))); } });
				 */

				viewSquare.setAttribute("cross", crossComponent);
				String event = "self.getAttribute(\"cross\").setClickedSquare(\""
						+ formatedPosition + "\");";
				event += crossComponent.getSquareClickEvent();
				viewSquare.addEventHandler("onClick", (new EventHandler(
						new ZScript("java", event))));

				viewSquare.appendChild(viewSquareText);
				crossLine.appendChild(viewSquare);
			}
			crosswordContainer.appendChild(crossLine);
		}
	}

	private void mountUserCrossWord(CrossWord crossword,
			Vlayout crosswordContainer) {
		for (int y = 1; y <= crossword.getCrossYDimension(); y++) {
			Hlayout crossLine = new Hlayout();

			for (int x = 1; x <= crossword.getCrossXDimension(); x++) {

				final Div viewSquare = new Div();
				Label viewSquareText = new Label();
				String formatedPosition = x + "." + y;
				Square square = crossword.getSquares().get(formatedPosition);
				viewSquare.setId(formatedPosition);
				if (square == null) {
					viewSquare.setSclass("invisible");
					viewSquareText.setValue("");
				} else {
					if (square.getFixed()) {
						viewSquareText.setValue(square.getValue());
					} else {
						viewSquareText.setValue("");
					}

					viewSquare.addEventListener("onClick",
							new EventListener<Event>() {
								public void onEvent(Event event)
										throws Exception {
									squareController
											.viewSquareClick(viewSquare);
								}
							});
				}
				viewSquare.appendChild(viewSquareText);
				crossLine.appendChild(viewSquare);
			}
			crosswordContainer.appendChild(crossLine);
		}
	}

	public Boolean submitCrossWord() {
		Boolean gameVictory = true;
		// /Poderia ser um método
		if (this.divSelected != null) {
			squareController.changeSquareToReadMode(this.divSelected);
		}
		// /End Poderia ser um método
		squareController.saveSquareValues();
		// conferir respostas
		this.gameLogical.giveAnswers();
		CrossWord crossword = this.gameLogical.getCrossword();
		squareController.clearSquareView();
		for (Answer answer : crossword.getAnswers()) {
			if (!answer.getAnsweredCorrectly()) {
				gameVictory = false;
			}
			for (Square squareByAnswer : answer.getSquares()) {
				Div squareView = squareController.getViewSquare(squareByAnswer
						.getId());
				if (answer.getAnsweredCorrectly()
						|| (squareView.getSclass() != null && squareView
								.getSclass().equals("correctSquare"))) {
					squareController.getViewSquare(squareByAnswer.getId())
							.setSclass("correctSquare");
				} else {
					squareController.getViewSquare(squareByAnswer.getId())
							.setSclass("");
				}
			}
		}
		return gameVictory;
	}

	// /Get and Setters
	public CrossWordLogical getGameLogical() {
		return gameLogical;
	}

	public void setGameLogical(CrossWordLogical gameLogical) {
		this.gameLogical = gameLogical;
	}

	public Div getDivSelected() {
		return divSelected;
	}

	public void setDivSelected(Div divSelected) {
		this.divSelected = divSelected;
	}

	public String getDivSelectedValue() {
		return divSelectedValue;
	}

	public void setDivSelectedValue(String divSelectedValue) {
		this.divSelectedValue = divSelectedValue;
	}

	public TypeAnswer getDirectionalAnswerSelected() {
		return directionalAnswerSelected;
	}

	public void setDirectionalAnswerSelected(
			TypeAnswer directionalAnswerSelected) {
		this.directionalAnswerSelected = directionalAnswerSelected;
	}

	public Vlayout getActualContainer() {
		return actualContainer;
	}

	public void setActualContainer(Vlayout actualContainer) {
		this.actualContainer = actualContainer;
	}

	public void setCrossWordForm(ZKCrossWordForm crosswordForm) {
		this.crosswordForm = crosswordForm;

	}

}
