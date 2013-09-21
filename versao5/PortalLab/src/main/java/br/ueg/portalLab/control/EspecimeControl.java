package br.ueg.portalLab.control;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.reflection.Reflection;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.Colecao;
import br.ueg.portalLab.model.Especime;
import br.ueg.portalLab.model.EspecimeDeterminador;
import br.ueg.portalLab.model.Estacao;
import br.ueg.portalLab.model.ItemGeografico;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.model.Laboratorio;
import br.ueg.portalLab.util.control.EspecimeValidatorControl;

@Service
@Scope("desktop")
public class EspecimeControl extends Control<Especime> {

	@Override
	public boolean actionCanceledit(
			SubControllerManager<Especime> subControllersManager) {
		
		ItemTaxonomico itta = ((Especime) this.getMapFields().get("entity")).getEpitetoEspecifico();
		this.refreshFields(itta);
		
		return super.actionCanceledit(subControllersManager);
	}

	@SuppressWarnings("unchecked")
	public void refreshFields(Entity entity){
		if(entity==null)return;
		for(Field f: Hibernate.getClass(entity).getDeclaredFields()){
			if(f.getType().getSimpleName().equalsIgnoreCase("set")){
				String fieldName = f.getName();
				try {
					Set<Entity> fieldValue = (Set<Entity>)Reflection.getFieldValue(entity, fieldName);
					Set<Entity> aux = new HashSet<Entity>(fieldValue);
					for(Entity e: aux){
						if(e.isNew()){
							fieldValue.remove(e);
						}
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
			}
		}
	}
	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.control.Control#actionSearch(br.ueg.builderSoft.control.SubControllerManager)
	 */
	@Override
	public boolean actionSearch(
			SubControllerManager<Especime> subControllerManager) {
		// TODO Auto-generated method stub
		return super.actionSearch(subControllerManager);
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.control.Control#actionFindByCriteria(br.ueg.builderSoft.control.SubControllerManager)
	 */
	@Override
	public boolean actionFindByCriteria(
			SubControllerManager<Especime> subControllerManager) {
		if ( this.getMapFields().get("searchEntity") != null ) {
			ListingControl<Especime> listingControl = (ListingControl<Especime>) subControllerManager.getListingControl();
			listingControl.setList(getPersistence().findByEntity((Especime) this.getMapFields().get("searchEntity")));
			listingControl.setListing(true);
			return true;
		} else {
			subControllerManager.getMessagesControl().addMessageError("Busca");
			return false;
		}
	}

	@Override
	public boolean actionSave(
			SubControllerManager<Especime> subControllerManager) {
		boolean retorno = false;
		Especime entity = (Especime) this.getMapFields().get("entity");
		
		if(!entity.isNew()){
				retorno =  super.actionSave(subControllerManager);				
		}else{
				retorno = this.saveDeterminadoresForNewEntity(entity, subControllerManager);					
		}
		return retorno;
	}
	
	public boolean saveDeterminadoresForNewEntity(Especime entity, SubControllerManager<Especime> subControllerManager){
		boolean retorno = false;
		Set<EspecimeDeterminador> espDeterminadores = entity.getEspecimeDeterminadores();
		entity.setEspecimeDeterminadores(new HashSet<EspecimeDeterminador>());
		retorno = super.actionSave(subControllerManager);
		if(retorno){
			for(EspecimeDeterminador espDet: espDeterminadores){
				espDet.setEspecime(entity);
			}
			entity.setEspecimeDeterminadores(espDeterminadores);
			retorno = super.actionSave(subControllerManager);
		}				
		return retorno;
	}
	
	private Especime selectedEspecime;
	
	
	public EspecimeControl(MessagesControl control) {
		super(control);		 
	}
	public EspecimeControl(){
		
	}
	

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.control.Control#getListValidator()
	 */
	@Override
	public List<SubController> getListValidator() {
		List<SubController> validators = super.getListValidator();

		validators.add((SubController) new EspecimeValidatorControl(this.getMessagesControl(),1));
		return validators;
	}
	
	
	/**
	 * Verifica se a entidade passada está presente na lista(feito por 
	 * problemas de utilização de Lazy no hibernate)
	 * @param entity
	 * @param list
	 * @return boolean retorno se a entidade está presente
	 */
	public boolean isEntityPresentInList(Entity entity, Set<Entity> list){
		if(list== null) return false;
		for(Entity localEntity : list){
			if(localEntity.getId().equals(entity.getId())){
				return true;
			}
		}
		return false;

	}
	
	/**
	 * Metodo utilizado para retorar a lista de itens da entidade
	 * que não estão associados ao Especime ainda.
	 * O metodo pega o tipo do item da lista e solicita uma listagem 
	 * dessa entidade removendo os itens presente na lista.
	 * Obs.: Esse metodo deve ser chamada com um lista que tenha
	 * pelo menos um item, pois o item será utilizado para determinar o tipo
	 * @param list
	 * @return List<Entity> lista de entidades 
	 */
	@SuppressWarnings("unchecked")
	public List<Entity> getListToEntityField(Set<Entity> list){
		if (list==null || (list!=null && list.size()==0)) return null;
		
		GenericDAO<Entity> genericDAO = (GenericDAO<Entity>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		
		Entity entityType = list.iterator().next();
		
		List<Entity> resultList = new ArrayList<Entity>(0);
		
		for(Entity e : genericDAO.getList(Hibernate.getClass(entityType))){
			if(!this.isEntityPresentInList(e, list)){
				resultList.add(e);
			}
		}
		
		return resultList;
	}
	
	/**
	 * Metodo utilizado para retorar a lista de itens da entidade
	 * que não estão associados ao Especime ainda.
	 * O metodo faz uma listagem de objetos do tipo entity passado como parametro
	 * e remove os itens que pertençam a lista passada, sendo que um atributo de cada 
	 * item da lista deve ser do tipo da entidade passada.	 
	 * @param list
	 * @param entityClass
	 * @return List<Entity> Lista de entidade
	 */
	
	public List<Entity> getListToEntityField(Set<Entity> list, Class<? extends Entity> entityClass){

		Set<Entity> auxList = new HashSet<Entity>(0);
		
		for(Entity entity : list){
			Entity auxEntity=null;
			try {
				auxEntity = (Entity) Reflection.getFieldValue(entity, entityClass.getSimpleName().toLowerCase());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				auxEntity = entity;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if(auxEntity!=null){
				auxList.add(auxEntity);
			}
		}
		if(auxList.size()==0){
			Entity e;
			try {
				e = entityClass.newInstance();
				e.setId(0L);
				auxList.add(e);
			} catch (InstantiationException e1) {				
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}

		}
		
		return this.getListToEntityField(auxList);
	}
	/**
	 * @return GenericDAO<Colecao> DAO
	 */
	private GenericDAO<Colecao> getColecaoDAO() {
		return (GenericDAO<Colecao>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	}
		
	@SuppressWarnings("unchecked")
	public List<Colecao> getColecaoList(Laboratorio lab){
		GenericDAO<Colecao> colecaoDAO = this.getColecaoDAO();
		Colecao gef = new Colecao();
		gef.setLaboratorio(lab);
		List<Colecao> findByEntity = colecaoDAO.findByEntity(gef);
		Collections.sort(findByEntity);
		//List<Colecao> findByEntity = colecaoDAO.getListFK(gef.getClass());
		return findByEntity;
	}
	
	
//	public void cancelEditEntity(){
//		this.getPersistence().getSession().refresh(selctedEspecime);
//	}
	
	
	/**
	 * @return the selectedGrupoUsuario
	 */
	public Especime getSelectedEspecime() {
		//this.getPersistence().refresh(selectedEspecime);
		//selectedEspecime =  (Especime) this.getPersistence().findByEntity(selectedEspecime).get(0);
		return selectedEspecime;
	}
	/**
	 * @param selectedEspecime the selectedGrupoUsuario to set
	 */
	public void setSelectedEspecime(Especime selectedEspecime) {
		this.selectedEspecime = selectedEspecime;
	}
	
	/* metodos para aba geografia */
	@SuppressWarnings("unchecked")
	public GenericDAO<ItemGeografico> getItemGeograficoDAO(){
		return (GenericDAO<ItemGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	}
	
	public List<ItemGeografico> getPaisList(){
		GenericDAO<ItemGeografico> itemGeograficoDAO = this.getItemGeograficoDAO();
		String qry = "from ItemGeografico i where i.pai is null order by i.nome";	
		List<ItemGeografico> list = itemGeograficoDAO.findByHQL(qry);
		if(list==null) new ArrayList<ItemGeografico>(0);
		return list;
	}
	
	public List<ItemGeografico> getFilhoList(ItemGeografico pai){
		List<ItemGeografico> list = null;
		if(pai!=null){
			GenericDAO<ItemGeografico> itemGeograficoDAO = this.getItemGeograficoDAO();
			String qry = "from ItemGeografico i where i.pai="+pai.getId()+" order by i.nome";
			list = itemGeograficoDAO.findByHQL(qry);
		}
		if(list==null) new ArrayList<ItemGeografico>(0);
		return list;
	}
	
	/* metodos guia taxonomia */
	@SuppressWarnings("unchecked")
	public GenericDAO<ItemTaxonomico> getItemTaxonomicoDAO(){
		return (GenericDAO<ItemTaxonomico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	}
	
	public List<ItemTaxonomico> getReinoList(){
		GenericDAO<ItemTaxonomico> itemTaxonomicoDAO = this.getItemTaxonomicoDAO();
		String qry = "from ItemTaxonomico i where i.pai is null order by i.nome";	
		List<ItemTaxonomico> list = itemTaxonomicoDAO.findByHQL(qry);
		if(list==null) new ArrayList<ItemGeografico>(0);
		return list;
	}
	
	public List<ItemTaxonomico> getFilhoList(ItemTaxonomico pai, int nivel){
		List<ItemTaxonomico> list = null;
		if(pai!=null){
			GenericDAO<ItemTaxonomico> itemTaxonomicoDAO = this.getItemTaxonomicoDAO();
			String qry = "from ItemTaxonomico i where i.pai="+pai.getId()+" and i.nivelTaxonomico="+String.valueOf(nivel)+" order by i.nome";
			list = itemTaxonomicoDAO.findByHQL(qry);
		}
		if(list==null) new ArrayList<ItemTaxonomico>(0);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Entity> getEstacaoList(){
		GenericDAO<Entity> estacaoDAO = (GenericDAO<Entity>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		return estacaoDAO.getList(Estacao.class);
	}
	
	/* metodos guia Coleta */
	@SuppressWarnings("unchecked")
	public GenericDAO<Especime> getEspecimeDAO(){
		return (GenericDAO<Especime>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	}
	
	public String getLastCollectionList(Colecao col){
		List<Especime> list = null;
		if(col != null){
			GenericDAO<Especime> especimeDAO = this.getEspecimeDAO();
			String qry = "select top 1 CAST(SUBSTRING(e.codigo_catalogo, CHARINDEX('.', e.codigo_catalogo)+1, LEN(e.codigo_catalogo)) AS INT) as num_catalogo from especime e where codigo_catalogo LIKE '" + col.getSigla() + "%' order by CAST(SUBSTRING(e.codigo_catalogo, CHARINDEX('.', e.codigo_catalogo)+1, LEN(e.codigo_catalogo)) AS INT) desc";
			System.out.println(qry);
			list = especimeDAO.findByNativeSQL(qry);
		}
		return this.getCodigoCatalogoName(col, list);
	}
	
	private String getCodigoCatalogoName(Colecao col, List<Especime> list){
		String codigoColeta = col.getSigla() + ".";
		if(list == null || list.isEmpty()){
			codigoColeta += 1;
		} else {
			Object codigo = list.get(0);
			codigoColeta += ((Integer)codigo + 1); 
		}
		return codigoColeta;
	}
}
