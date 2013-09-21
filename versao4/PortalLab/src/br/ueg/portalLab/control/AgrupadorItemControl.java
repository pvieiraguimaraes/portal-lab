package br.ueg.portalLab.control;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.portalLab.model.AgrupadorItensTaxonomicos;

@Service
public class AgrupadorItemControl extends Control<AgrupadorItensTaxonomicos> {

	public AgrupadorItemControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
	}
	
	public AgrupadorItemControl(){
		
	}
	
	@Override
	public HashMap<String, Object> getMapFields() {
		if(super.getMapFields() == null){
			setMapFields(new HashMap<String, Object>());
		}
		return super.getMapFields();
	}
	
}
