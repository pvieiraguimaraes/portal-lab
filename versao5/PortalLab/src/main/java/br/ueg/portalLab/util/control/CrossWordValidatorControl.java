package br.ueg.portalLab.util.control;

import java.util.HashMap;
import java.util.List;

import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.ValidatorControl;
import br.ueg.portalLab.model.jogo.cruzada.Answer;
import br.ueg.portalLab.model.jogo.cruzada.CrossWord;
import br.ueg.portalLab.model.jogo.cruzada.Square;
import br.ueg.portalLab.model.jogo.cruzada.TypeAnswer;

public class CrossWordValidatorControl extends ValidatorControl {
	public CrossWordValidatorControl(MessagesControl pMessagesControl,
			int orderValidate, List<String> actionsToValidate) {
		super(pMessagesControl, orderValidate, actionsToValidate);
	}

	public CrossWordValidatorControl(MessagesControl pMessagesControl,
			int orderValidate) {
		super(pMessagesControl, orderValidate);
		actionsToValidate.add("validateAnswer");
	}

	@Override
	public boolean doAction(HashMap<String, Object> mapFields, String action) {
		if (action.equalsIgnoreCase("validateAnswer")) {
			if (validarTamanhoAnswer(mapFields)) {
				if (validarInterceccaoAnswer(mapFields)) {
					return true;
				} else {
					this.messagesControl
							.addMessageError("interseccao_resposta");
					return false;
				}
			} else {
				this.messagesControl.addMessageError("resposta_grande");
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean validarInterceccaoAnswer(HashMap<String, Object> mapFields) {
		Answer answer = (Answer) mapFields.get("actualAnswer");
		HashMap<String, Square> squares = (HashMap<String, Square>) mapFields
				.get("squares");
		for (int i = 0; i < answer.getValue().length(); i++) {
			String squarePosition;
			if (answer.getType() == TypeAnswer.HORIZONTAL)
				squarePosition = Integer.toString(answer.getStartPositionX()
						+ i)
						+ "." + answer.getStartPositionY();
			else
				squarePosition = answer.getStartPositionX() + "."
						+ Integer.toString(answer.getStartPositionY() + i);
			Square square = squares.get(squarePosition);
			if (square != null) {

				if (answer.getType() == TypeAnswer.HORIZONTAL) {
					if (square.getVerticalAnswer() != null
							|| square.getHorizontalAnswer() != null
							&& square.getHorizontalAnswer() != answer) {
						if (!validarInterceccaoSquare(square, answer, i))
							return false;
					}
				} else {
					if (square.getHorizontalAnswer() != null
							|| (square.getVerticalAnswer() != null && square
									.getVerticalAnswer() != answer)) {
						if (!validarInterceccaoSquare(square, answer, i))
							return false;
					}
				}

			}
		}

		return true;
	}

	private boolean validarInterceccaoSquare(Square square, Answer answer,
			Integer i) {
		Integer letterPosition;
		if (answer.getType() == TypeAnswer.HORIZONTAL)
			letterPosition = answer.getStartPositionX() + i;
		else
			letterPosition = answer.getStartPositionY() + i;
		if (square.getValue() != null
				&& !(square.getValue().equalsIgnoreCase(answer.getValue()
						.substring(i, i + 1))))
			//.substring(letterPosition - 1, letterPosition) antigamente feito assim
			return false;
		return true;

	}

	private boolean validarTamanhoAnswer(HashMap<String, Object> mapFields) {
		Answer answer = (Answer) mapFields.get("actualAnswer");
		Integer xCrossWordLength = ((CrossWord) mapFields.get("entity"))
				.getCrossXDimension();
		Integer yCrossWordLength = ((CrossWord) mapFields.get("entity"))
				.getCrossYDimension();
		if (answer.getType() == TypeAnswer.HORIZONTAL) // x
		{
			if (answer.getValue().length() > xCrossWordLength
					- (answer.getStartPositionX() - 1)) {
				return false;
			}
		} else {
			if (answer.getValue().length() > yCrossWordLength
					- (answer.getStartPositionY() - 1)) {
				return false;
			}
		}
		return true;
	}

}
