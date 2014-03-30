package br.ueg.portalLab.view.component.jogos.cruzadas;

import java.util.HashMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import br.ueg.portalLab.model.jogo.cruzada.Answer;
import br.ueg.portalLab.model.jogo.cruzada.Square;
import br.ueg.portalLab.model.jogo.cruzada.TypeAnswer;
import br.ueg.portalLab.utils.FormaterText;

public class ZKSquareComponentController {

	ZKCrossWord crossword;
	ZKCrossWordForm crosswordForm;
	HashMap<String,String> utimateSquaresValue = new HashMap<String,String>();

	public ZKSquareComponentController(ZKCrossWord crossword) {
		this.crossword = crossword;
	}

	public void viewSquareClick(Div div) {
		this.utimateSquaresValue.put(div.getId(), getViewSquareValue(getSquareByViewDiv(div).getId()));
		if (crossword.getDivSelected() != null) {
			changeSquareToReadMode(
					crossword.getDivSelected(),
					getViewSquareValue(getSquareByViewDiv(
							crossword.getDivSelected()).getId()));
		}
		changeSquareToEditMode(div);
		Square square = getSquareByViewDiv(div);

		Answer horizontalAnswer = square.getHorizontalAnswer();
		Answer verticalAnswer = square.getVerticalAnswer();
		String firstSquareVerticalID = "";
		String firstSquareHorizontalID = "";

		if (horizontalAnswer != null) {
			Integer x = square.getHorizontalAnswer().getStartPositionX();
			Integer y = square.getHorizontalAnswer().getStartPositionY();
			firstSquareHorizontalID = x + "." + y;
		}
		if (verticalAnswer != null) {
			Integer x = square.getVerticalAnswer().getStartPositionX();
			Integer y = square.getVerticalAnswer().getStartPositionY();
			firstSquareVerticalID = x + "." + y;
		}

		// /Caso seja o primeiro quadrado
		// /Ou não haja pergunta vertical
		// Ou não seja selecionado o primeiro quadrado
		if (horizontalAnswer != null
				&& (verticalAnswer == null
						|| square.getId().equals(firstSquareHorizontalID) || !square
						.getId().equals(firstSquareVerticalID))) {
			this.crosswordForm.selectHint(square.getHorizontalAnswer());
			crossword.setDirectionalAnswerSelected(TypeAnswer.HORIZONTAL);
		} else {
			this.crosswordForm.selectHint(square.getVerticalAnswer());
			crossword.setDirectionalAnswerSelected(TypeAnswer.VERTICAL);
		}
		crossword.setDivSelected(div);
	}

	public void changeSquareToReadMode(Div div) {
		changeSquareToReadMode(div, "");
	}

	public void changeSquareToReadMode(Div div, String value) {
		div.getChildren().clear();
		Label viewSquareText = new Label();
		if (value.equals("") && this.utimateSquaresValue.containsKey(div.getId()))
			value = this.utimateSquaresValue.get(div.getId());
		viewSquareText.setValue(value);
		div.appendChild(viewSquareText);
	}

	public void changeSquareToEditMode(Div viewSquare) {
		final Div div = viewSquare;
		
		div.getChildren().clear();
		final Textbox viewSquareTextbox = new Textbox();
		viewSquareTextbox.setMaxlength(1);
		viewSquareTextbox.setSclass("text-edit");
		viewSquareTextbox.addEventListener("onChanging",
				new EventListener<InputEvent>() {
					public void onEvent(InputEvent event) throws Exception {
						changeSquareToReadMode(div, event.getValue()
								.toUpperCase());
						goToNextSquare(div);
					}
				});
		div.appendChild(viewSquareTextbox);
		viewSquareTextbox.focus();
	}

	public Square getSquareByViewDiv(Div div) {
		return crossword.getGameLogical().getCrossword().getSquares()
				.get(div.getId());
	}

	public void goToNextSquare(Div div) {
		Integer x = FormaterText.getXPosition(div.getId());
		Integer y = FormaterText.getYPosition(div.getId());
		String formatedNextDivId;

		if (crossword.getDirectionalAnswerSelected() == TypeAnswer.HORIZONTAL)
			x++;
		else
			y++;
		formatedNextDivId = x + "." + y;

		if (crossword.getGameLogical().getCrossword().getSquares()
				.get(formatedNextDivId) != null) {
			Div nextViewSquare = (Div) crossword.getActualContainer()
					.getFellow(formatedNextDivId);
			changeSquareToEditMode(nextViewSquare);
		} else {
			crossword.setDirectionalAnswerSelected(null);
			crossword.setDivSelected(null);
		}
	}

	public void saveSquareValues() {
		for (String squareID : crossword.getGameLogical().getCrossword()
				.getSquares().keySet()) {
			Square square = (Square) crossword.getGameLogical().getCrossword()
					.getSquares().get(squareID);
			square.setValue(getViewSquareValue(squareID));
		}
	}

	private String getViewSquareValue(String squareID) {
		Div squareView = (Div) crossword.getActualContainer().getFellow(
				squareID);
		Component textBox = squareView.getChildren().get(0);
		String viewSquareValue = "";
		if (textBox instanceof Label) {
			viewSquareValue = ((Label) textBox).getValue();
		} else if (textBox instanceof Textbox) {
			viewSquareValue = ((Textbox) textBox).getValue();
		}
		return viewSquareValue;
	}

	public Div getViewSquare(String squareID) {
		return (Div) crossword.getActualContainer().getFellow(squareID);
	}

	public void clearSquareView() {
		for (Square square : crossword.getGameLogical().getCrossword()
				.getSquares().values()) {
			Div viewSquare = getViewSquare(square.getId());
			changeSquareToReadMode(viewSquare,
					getViewSquareValue(square.getId()));
			viewSquare.setSclass("");
		}
	}

	public ZKCrossWordForm getCrosswordForm() {
		return crosswordForm;
	}

	public void setCrosswordForm(ZKCrossWordForm crosswordForm) {
		this.crosswordForm = crosswordForm;
	}
}
