package br.ueg.portalsite;

import java.util.ArrayList;
import java.util.List;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.sets.SpringFactory;

public class SiteControl<E extends Entity> {

	protected GenericDAO<Entity> genericDAO;

	public GenericDAO<Entity> getGenericDAO() {
		if(genericDAO == null)
			initializePersistence();
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO<Entity> genericDAO) {
		this.genericDAO = genericDAO;
	}

	public SiteControl() {
		initializePersistence();
	}

	@SuppressWarnings("unchecked")
	private void initializePersistence() {
		if (genericDAO == null)
			genericDAO = (GenericDAO<Entity>) SpringFactory.getInstance()
					.getBean("genericDAO", GenericDAO.class);
	}

	@SuppressWarnings("unchecked")
	public List<E> getListEntity(Entity entity) {
		List<E> resultList =  new ArrayList<>();
		if (entity != null) {
			getGenericDAO().getSession().close();
			resultList = (List<E>) getGenericDAO().getList(entity, null);
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<E> getListByHQL(String sql){
		List<E> resultList =  new ArrayList<>();
		getGenericDAO().getSession().close();
		resultList = (List<E>) getGenericDAO().findByHQL(sql, null);
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> getListByNativeSQL(String sql){
		List<E> resultList =  new ArrayList<>();
		getGenericDAO().getSession().close();
		resultList = (List<E>) getGenericDAO().findByNativeSQL(sql, null);
		return resultList;
	}

}
