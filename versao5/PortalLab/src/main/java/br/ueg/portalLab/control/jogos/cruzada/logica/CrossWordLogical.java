/**
 * 
 */
package br.ueg.portalLab.control.jogos.cruzada.logica;

import java.util.HashMap;

import br.ueg.portalLab.model.jogo.cruzada.Answer;
import br.ueg.portalLab.model.jogo.cruzada.CrossWord;
import br.ueg.portalLab.model.jogo.cruzada.Square;
import br.ueg.portalLab.model.jogo.cruzada.TypeAnswer;

/**
 * @author tiagoar
 *
 */

public class CrossWordLogical {

	private CrossWord crossword;
	
	public CrossWordLogical()
	{
		
	}
	
	public CrossWordLogical(CrossWord crossword)
	{
		newGame(crossword);
	}
	
	public void newGame(CrossWord crossword)
	{
	    this.crossword = crossword;	
	}
	
	public void mountSquares()
	{
		crossword.setSquares(new HashMap<String,Square>());
		for(Answer answer: this.crossword.getAnswers())
		{
			for(int i=0;i<answer.getValue().length();i++)
			{
				String squarePosition;
				if (answer.getType() == TypeAnswer.HORIZONTAL)
					squarePosition = Integer.toString(answer.getStartPositionX() + i) + "." + answer.getStartPositionY();
				else
					squarePosition = answer.getStartPositionX() + "." + Integer.toString(answer.getStartPositionY() + i);
				mountOneSquare(answer.getValue().substring(i,i+1), answer,squarePosition);
			}

		}
		///mountAllSquare
	}
	
	private void mountOneSquare(String squareCharacter,Answer answer,String squareFormatedPosition)
	{
		Square square;
		square = crossword.getSquares().get(squareFormatedPosition);
		if (square == null)
		{
			square = new Square();
			square.setFixed(answer.getFixe());
		}
		else
		{
			if (square.getFixed() != true)
			{
				square.setFixed(answer.getFixe());
			}
		}
		
		if (answer.getType() == TypeAnswer.HORIZONTAL)
			square.setHorizontalAnswer(answer);
		else
			square.setVerticalAnswer(answer);
		square.setValue(squareCharacter);
		square.setId(squareFormatedPosition);
		answer.getSquares().add(square);
		crossword.getSquares().put(squareFormatedPosition,square);
	}

	public CrossWord getCrossword() {
		return crossword;
	}

	public void giveAnswers() {
		for(Answer answer: this.crossword.getAnswers())
		{
			answer.setAnsweredCorrectly(compareOneAnswer(answer));
		}
	}
	
	private Boolean compareOneAnswer(Answer answer)
	{
		Boolean correctAnswer = true;
		for(int i=0;i<answer.getValue().length();i++)
		{
			String squarePosition;
			if (answer.getType() == TypeAnswer.HORIZONTAL)
				squarePosition = Integer.toString(answer.getStartPositionX() + i) + "." + answer.getStartPositionY();
			else
				squarePosition = answer.getStartPositionX() + "." + Integer.toString(answer.getStartPositionY() + i);
			if (!compareOneSquare(answer.getValue().substring(i,i+1), squarePosition))
			{
				correctAnswer = false;
			}
		}
		return correctAnswer;
	}
	
	private boolean compareOneSquare(String answerCharacter,String squareFormatedPosition)
	{
		Square square;
		square = crossword.getSquares().get(squareFormatedPosition);
		if (square.getValue().equalsIgnoreCase(answerCharacter))
		{
			return true;
		}
		else
			return false;
	}
	
}
