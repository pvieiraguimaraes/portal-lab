package br.ueg.portalLab.control.jogos.cruzada;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.portalLab.model.jogo.cruzada.Answer;
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
	
	@Override
	public boolean actionSave(
			SubControllerManager<CrossWord> subControllerManager) {
		boolean retorno = false;
		CrossWord entity = (CrossWord) this.getMapFields().get("entity");
		
		if(!entity.isNew()){
				retorno =  super.actionSave(subControllerManager);				
		}else{
				retorno = this.saveCrossWordForNewEntity(entity, subControllerManager);					
		}
		return retorno;
	}
	
	public boolean saveCrossWordForNewEntity(CrossWord entity, SubControllerManager<CrossWord> subControllerManager){
		boolean retorno = false;
		List<Answer> answers = entity.getAnswers();
		entity.setAnswers(new ArrayList<Answer>());
		retorno = super.actionSave(subControllerManager);
		if(retorno){
			for(Answer answer: answers){
				answer.setCrossword(entity);
			}
			entity.setAnswers(answers);
			retorno = super.actionSave(subControllerManager);
		}				
		return retorno;
	}
	
}
