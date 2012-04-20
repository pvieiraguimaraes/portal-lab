package br.ueg.builderSoft.security.control;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.security.model.Funcionalidade;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;

@Service
public class FuncionalidadeControl extends Control<Funcionalidade> {

	
	public FuncionalidadeControl(MessagesControl control) {
		super(control);
		 //control.addController(new UsuarioValidatorControl((MessagesControl)control.getController(MessagesControl.class),1));
	}
	public FuncionalidadeControl(){
		
	}
	@SuppressWarnings("unchecked")
	@Override
	protected GenericDAO<Funcionalidade> getPersistence() {
		if(persistence==null){
			this.persistence = (GenericDAO<Funcionalidade>) SpringFactory.getInstance().getBean("genericDAOSecurity", GenericDAO.class);
		}
		return persistence;
	}

}
