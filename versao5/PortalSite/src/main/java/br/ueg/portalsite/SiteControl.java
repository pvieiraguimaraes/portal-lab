package br.ueg.portalsite;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.regexp.internal.RESyntaxException;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.control.ItemTaxonomicoControl;

public class SiteControl<E extends Entity> {

	private ItemTaxonomicoControl<Entity> controlTaxonomico;

	protected GenericDAO<Entity> genericDAO;

	@SuppressWarnings("unchecked")
	public SiteControl() {
		controlTaxonomico = new ItemTaxonomicoControl<Entity>();
		controlTaxonomico.getRootClasseAtividade();
		genericDAO = (GenericDAO<Entity>) SpringFactory.getInstance().getBean(
				"genericDAO", GenericDAO.class);
	}

	@SuppressWarnings("unchecked")
	public List<E> getListEntity(Entity entity) {
		List<E> resultList =  new ArrayList<>();
		if (entity != null) {
			genericDAO.getSession().close();
			resultList = (List<E>) genericDAO.getList(entity, null);
		}
		return resultList;
	}
	
	public List<E> getListByHQL(String sql){
		List<E> resultList =  new ArrayList<>();
		genericDAO.getSession().close();
		resultList = (List<E>) genericDAO.findByHQL(sql, null);
		return resultList;
	}
	
	public List<?> getListByNativeSQL(String sql){
		List<E> resultList =  new ArrayList<>();
		genericDAO.getSession().close();
		resultList = (List<E>) genericDAO.findByNativeSQL(sql, null);
		return resultList;
	}

}
