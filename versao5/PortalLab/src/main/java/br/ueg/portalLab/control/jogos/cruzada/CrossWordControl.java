package br.ueg.portalLab.control.jogos.cruzada;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.portalLab.model.jogo.cruzada.CrossWord;
import br.ueg.portalLab.util.control.CrossWordValidatorControl;

@Service
public class CrossWordControl extends Control<CrossWord> {

	public CrossWordControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
	}
	
	public CrossWordControl(){
		
	}
	
	///Action passa por uma validação, portanto não poderá ser removido, ele é como uma "fachada"
	// para chamar a validação
	public boolean actionValidateanswer(SubControllerManager<CrossWord> subControllerManager) {
		return true;
	}
	
	public List<SubController> getListValidator(){
		List<SubController> list = super.getListValidator();
		list.add(new CrossWordValidatorControl(getMessagesControl(),1));
		return list;
	}
	
}
