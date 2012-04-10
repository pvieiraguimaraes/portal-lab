package br.ueg.builderSoft.persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
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
	
	Session currentSession;
	
	/**
	 * Método para setar o HibernateTemplate, necessário para ser instanciado pelo Spring
	 * @param hibernateTemplate
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
		
	}
	public Session getSession(){
		if(currentSession==null){
			currentSession = this.hibernateTemplate.getSessionFactory().openSession();
		}
		return currentSession;
	}

	@Override
	public long save(E entity) {
		return (Long) hibernateTemplate.save(entity);
	}

	@Override
	public void update(E entity) {
		hibernateTemplate.update(entity);
		getSession().clear();
	}

	@Override
	public void delete(E entity) {
		hibernateTemplate.delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByCriteria(E entity, String value) {
		List<E> searchs = new ArrayList<E>();
		
		Criteria criteria = this.getSession().createCriteria(entity.getClass());
		int numFilters =  entity.getSearchColumnTable(entity).size();
		Criterion conds[] = new Criterion[numFilters];
		for (int i = 0; i < numFilters; i++) {
			conds[i] = Restrictions.like(entity.getSearchColumnEntity(entity).get(i), "%"+value+"%");						
		}
		LogicalExpression orExp=null;
		
		if(numFilters>1){
			orExp = null;
			for(int i = 1 ; i<numFilters;i++ ){
				if(orExp==null){
					orExp = Restrictions.or(conds[0], conds[1]);
				}else{
					orExp = Restrictions.or(orExp, conds[i]);
				}
			}
			criteria.add(orExp);
		}else{
			criteria.add(conds[0]);
		}
		
		
		searchs.addAll(criteria.list());
		HashSet<E> h = new HashSet<E>(searchs);
		searchs.clear();
		searchs.addAll(h);
		return searchs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByHQL(E entity, String value) {
		List<E> searchs = new ArrayList<E>();
		for (int i = 0; i < entity.getSearchColumnTable(entity).size(); i++) {
			String hql = "from " + entity.getClass().getSimpleName() + " where "+ entity.getSearchColumnTable(entity).get(i) +" like '%"+value+"%'";
			Query qry = this.getSession().createQuery(hql);
			searchs.addAll(qry.list());
		}
		return searchs;
	}
	
	/**
	 * Executa uma Query livre.
	 * @param qry
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> findByHQL(String qry){
		Query vQry = this.getSession().createQuery(qry);
		return vQry.list();
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
		Criteria criteria = this.getSession().createCriteria(entity.getClass()).add(Restrictions.idEq(id));
		searchs = criteria.list();
		if(searchs.size()>0){
			return searchs.get(0);
		}
		return null;
	}

}
