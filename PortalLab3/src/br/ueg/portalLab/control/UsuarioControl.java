package br.ueg.portalLab.control;

import java.util.List;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.CategoriaUsuario;
import br.ueg.portalLab.util.control.UsuarioValidatorControl;

public class UsuarioControl<E extends Entity> extends Control<E> {

	GenericDAO<CategoriaUsuario> nivelGeoDAO = null;
	
	@SuppressWarnings("unchecked")
	public UsuarioControl(MessagesControl control) {
		super(control);
		 nivelGeoDAO = (GenericDAO<CategoriaUsuario>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.control.Control#getListValidator()
	 */
	@Override
	public List<SubController> getListValidator() {
		List<SubController> validators = super.getListValidator();

		validators.add((SubController) new UsuarioValidatorControl(this.getMessagesControl(),1));
		return validators;
	}

	
//	public Set<CategoriaUsuario> getListCategoriaUsuario(){
//		
//		Set<CategoriaUsuario> list = new HashSet<CategoriaUsuario>(nivelGeoDAO.getList(new CategoriaUsuario()));
//		return list;
//	}
//	public Set<CategoriaUsuario> getListCategoriausuario(String filterName){
//		Set<CategoriaUsuario> list = new HashSet<CategoriaUsuario>(nivelGeoDAO.findByHQL("from Categoria c where c.nome like '%"+filterName+"$'"));
//		return list;
//	}
}
