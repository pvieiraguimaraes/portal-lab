package br.ueg.portalLab.util.control;

import java.util.HashMap;
import java.util.List;

import org.zkoss.util.media.Media;

import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.ValidatorControl;
import br.ueg.portalLab.model.EntityMedia;

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
			return this.validCadMedia((EntityMedia<Media>) mapFields.get("entity"));
		}else{
			return true;
		}
	}
	public boolean validCadMedia(EntityMedia<Media> entityMedia){
		boolean retorno = true;
		if( (entityMedia.getFileName()!=null && entityMedia.getFileName().equals("") ) ||
			(entityMedia.getFileName()!=null && entityMedia.getFileName().equalsIgnoreCase(entityMedia.getDefaultMediaName()))	
		){
			retorno = false;
			String entityName = entityMedia.getClass().getSimpleName().toLowerCase();
			messagesControl.addMessageError(entityName+"_imagem");				
		}
		return retorno;
	}
	
	

}
