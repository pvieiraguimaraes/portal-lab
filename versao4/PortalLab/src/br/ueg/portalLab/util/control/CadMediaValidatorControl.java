package br.ueg.portalLab.util.control;

import java.util.HashMap;
import java.util.List;

import org.zkoss.util.media.Media;

import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.ValidatorControl;
import br.ueg.portalLab.model.EspecieMultimidia;

public class CadMediaValidatorControl extends ValidatorControl {


	public CadMediaValidatorControl(MessagesControl pMessagesControl, int orderValidate, List<String> actionsToValidate) {
		super(pMessagesControl, orderValidate, actionsToValidate);
	}
	public CadMediaValidatorControl(MessagesControl pMessagesControl, int orderValidate) {
		super(pMessagesControl, orderValidate);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean doAction(HashMap<String, Object> mapFields, String action) {
		if(action.equalsIgnoreCase("SAVE")){
			return this.validCadMedia((EspecieMultimidia<Media>) mapFields.get("entity"));
		}else{
			return true;
		}
	}
	public boolean validCadMedia(EspecieMultimidia<Media> especieMultimidia){
		boolean retorno = true;
		if( (especieMultimidia.getNome()!=null && especieMultimidia.getNome().equals("") ) ||
			(especieMultimidia.getNome()!=null && especieMultimidia.getNome().equalsIgnoreCase("especies.jpg"))	
		){
			retorno = false;
			messagesControl.addMessageError("especieimagem_imagem");				
		}
		return retorno;
	}
	
	

}
