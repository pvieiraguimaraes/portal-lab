package br.ueg.portalLab.control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.GenericControl;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.CategoriaUsuario;
import br.ueg.portalLab.util.control.UsuarioValidatorControl;

public class UsuarioControl<E extends Entity> extends Control<E> {

	GenericDAO<CategoriaUsuario> nivelGeoDAO = null;
	
	private List<SubController> subControllers2 = new ArrayList<SubController>();
	
	public UsuarioControl(GenericControl<E> control) {
		super(control);
		 nivelGeoDAO = (GenericDAO<CategoriaUsuario>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		 control.setValidator(new UsuarioValidatorControl(control.getMessages()));
		 
		 	subControllers2.add(control.getValidator());//0
			subControllers2.add(control.getListingControl());//1
			subControllers2.add(control.getMessages());//2
		control.setSubControllers(subControllers2);
	}

	@SuppressWarnings("unchecked")
	public Set<CategoriaUsuario> getListCategoriaUsuario(){
		
		Set<CategoriaUsuario> list = new HashSet<CategoriaUsuario>(nivelGeoDAO.getList(new CategoriaUsuario()));
		return list;
	}
	public Set<CategoriaUsuario> getListCategoriausuario(String filterName){
		Set<CategoriaUsuario> list = new HashSet<CategoriaUsuario>(nivelGeoDAO.findByHQL("from Categoria c where c.nome like '%"+filterName+"$'"));
		return list;
	}
}
