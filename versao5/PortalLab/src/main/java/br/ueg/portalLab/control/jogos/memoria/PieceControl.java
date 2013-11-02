package br.ueg.portalLab.control.jogos.memoria;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.portalLab.model.jogo.memoria.Piece;

@Service
public class PieceControl extends Control<Piece> {

	public PieceControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
	}
	
	public PieceControl(){
		
	}
	
}
