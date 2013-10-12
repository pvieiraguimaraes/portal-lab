package br.ueg.portalLab.util.control;

import java.util.HashMap;
import java.util.List;

import org.zkoss.util.media.Media;

import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.ValidatorControl;
import br.ueg.portalLab.model.EntityMedia;
import br.ueg.portalLab.model.FichaTecnica;

public class CadFichaTecnicaValidatorControl extends ValidatorControl {


	public CadFichaTecnicaValidatorControl(MessagesControl pMessagesControl, int orderValidate, List<String> actionsToValidate) {
		super(pMessagesControl, orderValidate, actionsToValidate);
	}
	public CadFichaTecnicaValidatorControl(MessagesControl pMessagesControl, int orderValidate) {
		super(pMessagesControl, orderValidate);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean doAction(HashMap<String, Object> mapFields, String action) {
		if(action.equalsIgnoreCase("SAVE")){
			return this.validCadFichaTecnica((FichaTecnica) mapFields.get("entity"));
		}else{
			return true;
		}
	}
	public boolean validCadFichaTecnica(FichaTecnica entityMedia){
		boolean retorno = true;
		if( (entityMedia.getFileNameVerso()!=null && entityMedia.getFileNameVerso().equals("") ) ||
			(entityMedia.getFileNameVerso()!=null && entityMedia.getFileNameVerso().equalsIgnoreCase(entityMedia.getDefaultMediaName())) ||
			(entityMedia.getFileNameVerso()==null)	
		){
			retorno = false;
			messagesControl.addMessageError("fichatecnica_verso_imagem");				
		}
		return retorno;
	}
	
	

}
