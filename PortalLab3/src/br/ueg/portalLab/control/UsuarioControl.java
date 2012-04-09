package br.ueg.portalLab.control;

import java.util.HashSet;
import java.util.Set;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.GenericControl;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.CategoriaUsuario;
import br.ueg.portalLab.util.control.UsuarioValidatorControl;

public class UsuarioControl<E extends Entity> extends Control<E> {

	GenericDAO<CategoriaUsuario> nivelGeoDAO = null;
	
	@SuppressWarnings("unchecked")
	public UsuarioControl(GenericControl<E> control) {
		super(control);
		 nivelGeoDAO = (GenericDAO<CategoriaUsuario>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		 control.addController(new UsuarioValidatorControl((MessagesControl)control.getController(MessagesControl.class),1));
	}

	
	public Set<CategoriaUsuario> getListCategoriaUsuario(){
		
		Set<CategoriaUsuario> list = new HashSet<CategoriaUsuario>(nivelGeoDAO.getList(new CategoriaUsuario()));
		return list;
	}
	public Set<CategoriaUsuario> getListCategoriausuario(String filterName){
		Set<CategoriaUsuario> list = new HashSet<CategoriaUsuario>(nivelGeoDAO.findByHQL("from Categoria c where c.nome like '%"+filterName+"$'"));
		return list;
	}
}
