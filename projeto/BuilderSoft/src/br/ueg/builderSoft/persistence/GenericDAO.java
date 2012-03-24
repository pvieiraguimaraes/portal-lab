package br.ueg.builderSoft.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import br.ueg.builderSoft.model.Entity;

/**
 * Classe responsável pela persistência, faz o CRUD para qualquer entidade que extenda
 * Entity
 * 
 * @author Diego
 *
 * @param <Entity>
 */
public class GenericDAO<E extends Entity> implements IGenericDAO<E>{

	private HibernateTemplate hibernateTemplate;
	
	/**
	 * Método para setar o HibernateTemplate, necessário para ser instanciado pelo Spring
	 * @param hibernateTemplate
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public long save(E entity) {
		return (Long) hibernateTemplate.save(entity);
	}

	@Override
	public void update(E entity) {
		hibernateTemplate.update(entity);
	}

	@Override
	public void delete(E entity) {
		hibernateTemplate.delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByCriteria(E entity, String value) {
		List<E> searchs = new ArrayList<E>();
		for (int i = 0; i < entity.getSearchColumnTable(entity).size(); i++) {
			Criteria criteria = this.hibernateTemplate.getSessionFactory().openSession().createCriteria(entity.getClass()).add(Restrictions.like(entity.getSearchColumnEntity(entity).get(i), "%"+value+"%"));
			searchs.addAll(criteria.list());
		}
		return searchs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByHQL(E entity, String value) {
		List<E> searchs = new ArrayList<E>();
		for (int i = 0; i < entity.getSearchColumnTable(entity).size(); i++) {
			String hql = "from " + entity.getClass().getSimpleName() + " where "+ entity.getSearchColumnTable(entity).get(i) +" like '%"+value+"%'";
			searchs.addAll(this.hibernateTemplate.find(hql));
		}
		return searchs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> getList(E entity) {
		return (List<E>) hibernateTemplate.loadAll(entity.getClass());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getListFK(Class entity) {
		return hibernateTemplate.loadAll(entity);
	}
	
	@SuppressWarnings("unchecked")
	public E getByID(E entity, Long id){
		List<E> searchs = new ArrayList<E>();
		Criteria criteria = this.hibernateTemplate.getSessionFactory().openSession().createCriteria(entity.getClass()).add(Restrictions.idEq(id));
		searchs = criteria.list();
		if(searchs.size()>0){
			return searchs.get(0);
		}
		return null;
	}

}
