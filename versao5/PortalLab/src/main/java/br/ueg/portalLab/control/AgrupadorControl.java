package br.ueg.portalLab.control;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.Agrupador;

@Service
public class AgrupadorControl extends Control<Agrupador> {

	public AgrupadorControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
	}
	
	public AgrupadorControl(){
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean actionDeleteagrupadoritem(SubControllerManager<Agrupador> subControllerManager) {
		Control control = SpringFactory.getInstance().getBean("agrupadorItemControl",	AgrupadorItemControl.class);
		control.getMapFields().put("selectedEntity", getMapFields().get("selectedAgrupadorItem"));
		return control.actionDelete(subControllerManager);
	}
	
}
