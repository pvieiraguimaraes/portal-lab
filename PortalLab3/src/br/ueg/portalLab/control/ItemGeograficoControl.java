package br.ueg.portalLab.control;

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
@Service
public class ItemGeograficoControl<E extends Entity> extends Control<E> {

	GenericDAO<ItemGeografico> nivelGeoDAO = null;
	
	@SuppressWarnings("unchecked")
	public ItemGeograficoControl(MessagesControl control) {
		super(control);		 
		 //control.addController(new UsuarioValidatorControl((MessagesControl)control.getController(MessagesControl.class),1));
	}
	public ItemGeograficoControl(){
		
	}
	
	public GenericDAO<ItemGeografico> getItemGeograficoDAO(){
		if(this.nivelGeoDAO==null){
			nivelGeoDAO = (GenericDAO<ItemGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		}
		return nivelGeoDAO;
	}

	
//	public Set<CategoriaUsuario> getListCategoriaUsuario(){
//		
//		Set<CategoriaUsuario> list = new HashSet<CategoriaUsuario>(nivelGeoDAO.getList(new CategoriaUsuario()));
//		return list;
//	}
	public Set<Entity> getListItemGeograficosPai(NivelGeografico nivelGeografico){
		String qry = "";
		if(nivelGeografico.getPai()!=null){
			qry = "from ItemGeografico i where i.nivelGeografico="+nivelGeografico.getPai().getId();
		}else{
			qry = "from ItemGeografico i where i.nivelGeografico is null";
		}
		Set<Entity> list = new HashSet<Entity>(getItemGeograficoDAO().findByHQL(qry));
		return list;
	}
}
