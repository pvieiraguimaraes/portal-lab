package br.ueg.portalLab.security.control;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.ItemGeografico;
import br.ueg.portalLab.model.NivelGeografico;
import br.ueg.portalLab.security.model.CasoDeUso;
import br.ueg.portalLab.security.model.CasoDeUsoFuncionalidade;
import br.ueg.portalLab.security.model.Funcionalidade;

@Service
public class CasoDeUsoControl extends Control<CasoDeUso> {

	GenericDAO<CasoDeUso> casoDeUsoDAO = null;
	
	@SuppressWarnings("unchecked")
	public CasoDeUsoControl(MessagesControl control) {
		super(control);
		 //control.addController(new UsuarioValidatorControl((MessagesControl)control.getController(MessagesControl.class),1));
	}
	public CasoDeUsoControl(){
		
	}

	
//	public Set<CategoriaUsuario> getListCategoriaUsuario(){
//		
//		Set<CategoriaUsuario> list = new HashSet<CategoriaUsuario>(nivelGeoDAO.getList(new CategoriaUsuario()));
//		return list;
//	}
	@SuppressWarnings("unchecked")
	public Set<Funcionalidade> getFuncionalidadeList(){
		GenericDAO<Funcionalidade> funcionalidadeDAO = (GenericDAO<Funcionalidade>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		
		
		Set<Funcionalidade> list = new HashSet<Funcionalidade>(funcionalidadeDAO.getList(new Funcionalidade()));
		return list;
	}
}
