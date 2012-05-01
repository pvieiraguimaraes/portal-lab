package br.ueg.builderSoft.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Transient;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.builderSoft.util.reflection.Reflection;

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
	Transaction currentTransaction;
	
	/**
	 * Método para setar o HibernateTemplate, necessário para ser instanciado pelo Spring
	 * @param hibernateTemplate
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
		
	}
	public Session getSession(){
		if(currentSession==null || !currentSession.isOpen()){
			currentSession = this.hibernateTemplate.getSessionFactory().openSession();
		}
		return currentSession;
	}	

	@Override
	public long save(E entity) throws Exception {
		long retorno = 0L;
		Transaction ta=null;
		try{
			ta =this.getSession().beginTransaction();
			retorno = (Long)this.getSession().save(entity);
			ta.commit();
		}catch(org.hibernate.exception.ConstraintViolationException e){
			e.printStackTrace();
			if(ta!=null) ta.rollback();
			this.getSession().close();
			throw new DataIntegrityViolationException("(1)Violação de Integridade na Entidade:"+Reflection.getClassName(entity.getClass()));			
		}catch(org.springframework.dao.DataIntegrityViolationException e){
			e.printStackTrace();
			if(ta!=null) ta.rollback();
			this.getSession().close();
			throw new DataIntegrityViolationException("(2)Violação de Integridade na Entidade:"+Reflection.getClassName(entity.getClass()));
		}catch(org.springframework.dao.InvalidDataAccessResourceUsageException e){
			e.printStackTrace();
			if(ta!=null) ta.rollback();
			this.getSession().close();
			throw new DataIntegrityViolationException("(3)Violação de Integridade na Entidade:"+Reflection.getClassName(entity.getClass()));
		}catch (Exception e) {
			e.printStackTrace();
			if(ta!=null) ta.rollback();
			this.getSession().close();
			throw new Exception(e.getCause());
		}
		this.getSession().flush();
		getSession().clear();
		
		return retorno;
	}

	@Override
	public void update(E entity) throws Exception {
		Transaction ta=null;
		try{
			ta =this.getSession().beginTransaction();
			this.getSession().merge(entity);
			ta.commit();
		}catch(org.hibernate.exception.ConstraintViolationException e){
			e.printStackTrace();
			if(ta!=null) ta.rollback();
			this.getSession().close();
			throw new DataIntegrityViolationException("(1)Violação de Integridade na Entidade:"+Reflection.getClassName(entity.getClass()));			
		}catch(org.springframework.dao.DataIntegrityViolationException e){
			e.printStackTrace();
			if(ta!=null) ta.rollback();
			this.getSession().close();
			throw new DataIntegrityViolationException("(2)Violação de Integridade na Entidade:"+Reflection.getClassName(entity.getClass()));
		}catch(org.springframework.dao.InvalidDataAccessResourceUsageException e){
			e.printStackTrace();
			if(ta!=null) ta.rollback();
			this.getSession().close();
			throw new DataIntegrityViolationException("(3)Violação de Integridade na Entidade:"+Reflection.getClassName(entity.getClass()));
		}catch (Exception e) {
			e.printStackTrace();
			if(ta!=null) ta.rollback();
			this.getSession().close();
			throw new Exception(e.getCause());
		}
		this.getSession().flush();
		getSession().clear();
	}

	@Override
	public void delete(E entity) {
		//hibernateTemplate.delete(entity);
		Transaction ta =this.getSession().beginTransaction();
		getSession().clear();
		this.getSession().delete(entity);
		ta.commit();
		this.getSession().flush();
		
	}
	public void refresh(E entity){
		if(entity!=null && entity.getId()!=null){
			//refreshFields(entity);
			this.getSession().refresh(entity);
		}
	}
//	@SuppressWarnings("unchecked")
//	public void refreshFields(E entity){
//		for(Field f: entity.getClass().getDeclaredFields()){
//			if(f.getType().getSimpleName().equalsIgnoreCase("set")){
//				String fieldName = f.getName();
//				try {
//					Set<Entity> fieldValue = (Set<Entity>)Reflection.getFieldValue(entity, fieldName);
//					for(Entity e: fieldValue){
//						if(e.isNew()){
//							fieldValue.remove(e);
//						}
//					}
//					
//				} catch (SecurityException e) {
//					e.printStackTrace();
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (NoSuchMethodException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByCriteria(E entity, String value) {
		List<E> searchs = new ArrayList<E>();
		if(entity==null) return searchs;
		Session session = this.getSession();
		//session.flush();
		Criteria criteria = session.createCriteria(entity.getClass());
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
			Session session = this.getSession();
			//session.flush();
			Query qry = session.createQuery(hql);
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
		Session session = this.getSession();
		//session.flush();
		Query vQry = session.createQuery(qry);
		return vQry.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findByEntity(Entity entity){
		List<E> searchs = new ArrayList<E>();
		
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(entity.getClass());
		
		ArrayList<Criterion> conds = new ArrayList<Criterion>();

		for (Class<?> reflectedEntity = entity.getClass(); reflectedEntity != null; reflectedEntity = reflectedEntity.getSuperclass()) {
			Field[] fields = reflectedEntity.getDeclaredFields();
			
		
		
			for(int i = 0; i<fields.length; i++ ){
				Object fieldValue=null;
				try {
					fieldValue = Reflection.getFieldValue(entity, fields[i].getName());
					if(fieldValue!=null && fieldValue instanceof HashSet){
//						if( ((Long)fieldValue).doubleValue()==0L){
							fieldValue = null;
//						}
					}
					if(fields[i].getAnnotation(Transient.class)!=null){
						continue;
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				if(fieldValue!=null){
					conds.add(Restrictions.eq(fields[i].getName(), fieldValue));
				}
			}
				
			
			
		}
		for(Criterion c: conds){
			criteria.add(c);
		}

		
		//session.flush();		
		return criteria.list();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> getList(E entity) {
		Criteria criteria = this.getSession().createCriteria(entity.getClass());
		return (List<E>) criteria.list();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<E> getList(Class entity){
		Criteria criteria = this.getSession().createCriteria(entity);
		return (List<E>) criteria.list();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getListFK(Class entity) {
		return hibernateTemplate.loadAll(entity);
	}
	
	@SuppressWarnings("unchecked")
	public E getByID(E entity, Long id){
		List<E> searchs = new ArrayList<E>();
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(entity.getClass()).add(Restrictions.idEq(id));
		//session.flush();
		searchs = criteria.list();
		if(searchs.size()>0){
			return searchs.get(0);
		}
		return null;
	}

}
