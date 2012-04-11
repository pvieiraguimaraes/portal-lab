package br.ueg.portalLab.control;

import java.util.List;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;

public class TesteControl<E extends Entity> extends Control<E> {


	public TesteControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
	}

	public boolean actionNew(List<SubController> subControlers) {
		
		SubController teste = subControlers.get(3);
		teste.doAction(null, null);
		return true;
	}
	
	public boolean actionGuiliano(List<SubController> subControlers) {
		System.out.println("Voc  passou por aki");
		return true;
	}
}
