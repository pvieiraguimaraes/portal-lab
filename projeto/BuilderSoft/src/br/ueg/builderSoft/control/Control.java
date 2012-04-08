package br.ueg.builderSoft.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.ejb.event.EntityCallbackHandler;



import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.constant.ControllerType;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.sets.SpringFactory;

@SuppressWarnings("unchecked")
public class Control<E extends Entity>{
	
	private GenericControl<E> gControl;
	private GenericDAO<E> persistence;
	private HashMap<String, Object> mapFields;
	//construtores do subcontroller mandaram algo +
	//controlador principal para fazer modifica��es
	//DUVIDA: criar todos validadores para chamar seu m�todo principal no construtor
	//e mandar a instancia desse control pra ele setar o booleano continue
	//tenod que verificar esse a todo momento
	//assim no control terei um for para percorrer a lista de subcontrolers
	//para usar todos.
	
	/*
	 * Criar classe que extenda control, e dentro do construtor do filho da super do filho
	 * e aumentar no arraylist de valida��es.
	 */
	public Control(GenericControl<E> pGenericControl) {
		this.persistence = (GenericDAO<E>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		this.gControl = pGenericControl;
	}
	
	/**
	 * Metodo utilizado para buscar um lista de entidade baseado no tipo da entidade passada
	 * @param entity
	 * @return retorna uma lista de entity 
	 */
	public List<Entity> getListFKEntity(Entity entity){
		GenericDAO<Entity> genericDAO = SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);		
		return genericDAO.getList(entity);		
	}
	
	/**
	 * Metodo utilizado para buscar uma lista de entidade baseado no tipo da entidade passada
	 * usando o searchValue para filtrar os resultados
	 * @param entity
	 * @param searchValue
	 * @return
	 */
	public List<Entity> getListFKEntity(Entity entity, String searchValue){
		GenericDAO<Entity> genericDAO = SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);		
		return genericDAO.findByCriteria(entity,searchValue);
	}
	
	/**
	 * @param mapFields hashMap de fields da vis�o
	 */
	public void setMapFields(HashMap<String, Object> mapFields) {
		this.mapFields = mapFields;
	}

	/**
	 * M�todo que executa a a��o de incluir, fazendo as valida��es e logo depois chama a persitencia
	 * para salvar, caso seja a a��o de atualizar, chama o m�todo respons�vel para isso.
	 * @param Lista de sub-controladores.
	 */
	public boolean actionSave(List<SubController> subControlers) {
		E entity = (E) this.mapFields.get("entity");
		if (entity.getId() == 0) {
			boolean result = false;
			if (persistence.save(entity) != 0) {
				result = true;
			} 
			return result;
		} else {
			return actionUpdate(subControlers);
		}
	}
	
	/**
	 * M�todo que executa a a��o de atualizar, fazendo as valida��es e logo depois chama a persitencia
	 * para a a��o.
	 * @param Lista de sub-controladores.
	 */
	private boolean actionUpdate(List<SubController> subControlers) {
		persistence.update((E) this.mapFields.get("entity"));
		return true;
	}
	
	/**
	 * M�todo que executa a a��o de deletar, fazendo as valida��es e logo depois chama a persitencia
	 * para a a��o.
	 * @param Lista de sub-controladores.
	 */
	public boolean actionDelete(List<SubController> subControlers) {
		try {
			persistence.delete((E) this.mapFields.get("selectedEntity"));
			return true;
		}  catch (Exception e) {
			//TODO fazer o tratamento para dizer o motivo
			((MessagesControl) subControlers.get(ControllerType.MESSAGES)).addMessageError("CantDelete");
			return false;
		}
	}
	
	/**
	 * M�todo que executa a a��o de buscar, fazendo as valida��es e logo depois chama a persitencia
	 * para a a��o. � uma busca usando Criteria
	 * @param Lista de sub-controladores.
	 */
	public boolean actionFindByCriteria(List<SubController> subControlers) {//TODO: reescrever
		if ( this.mapFields.get("searchValue") != null && ! ((String) this.mapFields.get("searchValue")).isEmpty()) {
			ListingControl<E> listingControl = ((ListingControl<E>) subControlers.get(ControllerType.LISTING));
			listingControl.setList(persistence.findByCriteria((E) this.mapFields.get("entity"),   (String) this.mapFields.get("searchValue")));
			listingControl.setListing(true);
			return true;
		} else {
			((MessagesControl) subControlers.get(ControllerType.MESSAGES)).addMessageError("Busca");
			return false;
		}
	}
	
	/**
	 * M�todo que executa a a��o de buscar, fazendo as valida��es e logo depois chama a persitencia
	 * para a a��o. � uma busca usando HQL
	 * @param Lista de sub-controladores.
	 */
	public boolean actionFindByHQL(List<SubController> subControlers) {//TODO: reescrever
		if (this.mapFields.get("searchValue") != null && !((String) this.mapFields.get("searchValue")).isEmpty()) {
			ListingControl<E> listingControl = ((ListingControl<E>) subControlers.get(ControllerType.LISTING));
			listingControl.setList(persistence.findByHQL((E) this.mapFields.get("entity"), (String) this.mapFields.get("searchValue")));
			listingControl.setListing(true);
			return true;
		} else {
			((MessagesControl) subControlers.get(ControllerType.MESSAGES)).addMessageError("Busca");
			return false;
		}
	}
	
	/**
	 * M�todo que executa a a��o de listar, fazendo as valida��es e logo depois chama a persitencia
	 * para a a��o.
	 * @param Lista de sub-controladores.
	 */
	public boolean actionList(List<SubController> subControlers) {
		ListingControl<E> listingControl = ((ListingControl<E>) subControlers.get(ControllerType.LISTING));
		listingControl.setList(persistence.getList((E) this.mapFields.get("entity")));
		listingControl.setListing(true);
		listingControl.setSearch(false);
		return true;
	
	}
	
	/**
	 * M�todo que executa a a��o de Busca, fazendo as valida��es e logo depois chama a 
	 * persitencia para a a��o
	 * @param Lista de subControlers
	 * @return se a a��o foi executada
	 */
	public boolean actionSearch(List<SubController> subControlers) {
		if (actionFindByCriteria(subControlers)) {
			ListingControl<E> listingControl = ((ListingControl<E>) subControlers.get(ControllerType.LISTING));
			listingControl.setSearchValue((String) this.mapFields.get("searchValue"));
			listingControl.setSearch(true);
			return true;
		} else
			return false;
	}
	
	/**
	 * M�todo que executa a a��o de listar as Foreign Keys, fazendo as valida��es e logo depois 
	 * chama a persitencia para a a��o.
	 * @param Lista de sub-controladores.
	 */
	@SuppressWarnings("rawtypes")
	public boolean listFK(Class entity, List<SubController> subControlers) {
		((ListingControl<E>) subControlers.get(ControllerType.LISTING)).setListFk(persistence.getListFK(entity));
		return true;
	}
	
	/**
	 * M�todo que � necess�rio executar antes de qualquer m�todo dentro do Control
	 * executa as valida��es e aciona as mensagens caso haja algum erro
	 * @return true caso tudo esteja validado, false caso contr�rio.
	 */
	public boolean doAnyAction(List<SubController> subControllers, String action) {
		if (action.equalsIgnoreCase("SAVE")) {
			List<Object> atributes = new ArrayList<Object>();
			atributes.add((E) this.mapFields.get("entity"));
			if (!subControllers.get(ControllerType.VALIDATOR).doAction(atributes, action)) {
				//subControllers.get(ControllerType.MESSAGES).doAction(atributes, "cancel");//TODO verificar era action eu troceu por cancel(Guiliano) Acho que n�o precisa dessa mensagem aqui
				return false;
			}
		}
		return true;
	}
	
	public boolean actionAssociate(List<SubController> subControllers) {
		E selectedEntity = (E) this.mapFields.get("selectedEntity");
		this.gControl.associateEntityToAttributeView(selectedEntity);
		return true;
	}
}