package br.ueg.portalLab.control.jogos.memoria;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.Agrupador;
import br.ueg.portalLab.model.jogo.memoria.Memory;
import br.ueg.portalLab.model.jogo.memoria.Piece;

@Service
public class MemoriaControl extends Control<Memory> {

	public MemoriaControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
	}
	
	public MemoriaControl(){
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean actionSavepiece(SubControllerManager<Agrupador> subControllerManager) {
		Control control = SpringFactory.getInstance().getBean("pieceControl",	PieceControl.class);
		Piece p = (Piece) getMapFields().get("piece");
		p.setMemory((Memory) getMapFields().get("selectedEntity"));
		
		control.getMapFields().put("entity", p);
		return control.actionSave(subControllerManager);
	}
	
}
