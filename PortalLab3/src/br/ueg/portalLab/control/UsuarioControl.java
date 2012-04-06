package br.ueg.portalLab.control;

import java.util.HashSet;
import java.util.Set;


import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.GenericControl;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.CategoriaUsuario;

public class UsuarioControl<E extends Entity> extends Control<E> {


	public UsuarioControl(GenericControl<E> pGenericControl) {
		super(pGenericControl);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public Set<CategoriaUsuario> getListCategoriaUsuario(){
		GenericDAO<CategoriaUsuario> nivelGeoDAO = (GenericDAO<CategoriaUsuario>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		Set<CategoriaUsuario> list = new HashSet<CategoriaUsuario>(nivelGeoDAO.getList(new CategoriaUsuario()));
		return list;
	}
}
