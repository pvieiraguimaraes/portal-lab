package br.ueg.portalLab.util.control;

import java.util.HashMap;
import java.util.List;

import br.ueg.builderSoft.util.control.AbstractValidatorControl;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.portalLab.model.ItemTaxonomico;

public class ItemTaxonomicoValidatorControl extends AbstractValidatorControl {

	public ItemTaxonomicoValidatorControl(MessagesControl pMessagesControl, int orderValidate, List<String> actionsToValidate) {
		super(pMessagesControl, orderValidate, actionsToValidate);
	}

	public ItemTaxonomicoValidatorControl(MessagesControl pMessagesControl, int orderValidate) {
		super(pMessagesControl, orderValidate);
	}

	@Override
	public boolean doAction(HashMap<String, Object> mapFields, String action) {
		if(action.equalsIgnoreCase("DELETE")){
			return this.validar((ItemTaxonomico) mapFields.get("selectedEntity"));
		}
		return true;
	}
	public boolean validar(ItemTaxonomico it){
		boolean retorno = true;
		if(it.getFilhosItensTaxonomicos()!=null && it.getFilhosItensTaxonomicos().size()>0){
			retorno = false;
			messagesControl.addMessageError("classeatividade_temFilhos");
		}
		return retorno;
	}



}
