package br.ueg.builderSoft.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.constant.ControllerType;
import br.ueg.builderSoft.util.control.AbstractValidatorControl;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.control.ValidatorControl;
import br.ueg.builderSoft.util.sets.SpringFactory;

@SuppressWarnings("unchecked")
public class Control<E extends Entity>{
	
	private GenericControl<E> gControl;
	private GenericDAO<E> persistence;
	private HashMap<String, Object> mapFields;
	//construtores do subcontroller mandaram algo +
	//controlador principal para fazer modificações
	//DUVIDA: criar todos validadores para chamar seu método principal no construtor
	//e mandar a instancia desse control pra ele setar o booleano continue
	//tenod que verificar esse a todo momento
	//assim no control terei um for para percorrer a lista de subcontrolers
	//para usar todos.
	
	/*
	 * Criar classe que extenda control, e dentro do construtor do filho da super do filho
	 * e aumentar no arraylist de validações.
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
	 * @param mapFields hashMap de fields da visão
	 */
	public void setMapFields(HashMap<String, Object> mapFields) {
		this.mapFields = mapFields;
	}

	/**
	 * Método que executa a ação de incluir, fazendo as validações e logo depois chama a persitencia
	 * para salvar, caso seja a ação de atualizar, chama o método responsável para isso.
	 * @param Lista de sub-controladores.
	 */
	public boolean actionSave(SubControllerManager<E> subControllerManager) {
		E entity = (E) this.mapFields.get("entity");
		if (entity.getId() == 0) {
			boolean result = false;
			try{
				if (persistence.save(entity) != 0) {
					result = true;
				} 
			}catch(org.springframework.dao.DataIntegrityViolationException e){
				e.printStackTrace();
				subControllerManager.getMessagesControl().addMessageError("violacaoDeIntegridade");
				result = false;
			}catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
			return result;
		} else {
			return actionUpdate(subControllerManager);
		}
	}
	
	/**
	 * Método que executa a ação de atualizar, fazendo as validações e logo depois chama a persitencia
	 * para a ação.
	 * @param Lista de sub-controladores.
	 */
	private boolean actionUpdate(SubControllerManager<E> subControllerManager) {
		E entity = (E) this.mapFields.get("entity");

		boolean result = false;
		try{
			persistence.update(entity);
			result = true;			
		}catch(org.springframework.dao.DataIntegrityViolationException e){
			e.printStackTrace();
			subControllerManager.getMessagesControl().addMessageError("violacaoDeIntegridade");
			result = false;
		}catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;

	}
	
	/**
	 * Método que executa a ação de deletar, fazendo as validações e logo depois chama a persitencia
	 * para a ação.
	 * @param Lista de sub-controladores.
	 */
	public boolean actionDelete(SubControllerManager<E> subControllerManager) {
		try {
			persistence.delete((E) this.mapFields.get("selectedEntity"));
			return true;
		}  catch (Exception e) {
			//TODO fazer o tratamento para dizer o motivo
			subControllerManager.getMessagesControl().addMessageError("CantDelete");
			return false;
		}
	}
	
	/**
	 * Método que executa a ação de buscar, fazendo as validações e logo depois chama a persitencia
	 * para a ação. É uma busca usando Criteria
	 * @param Lista de sub-controladores.
	 */
	public boolean actionFindByCriteria(SubControllerManager<E> subControllerManager) {//TODO: reescrever
		if ( this.mapFields.get("searchValue") != null && ! ((String) this.mapFields.get("searchValue")).isEmpty()) {
			ListingControl<E> listingControl = (ListingControl<E>) subControllerManager.getListingControl();
			listingControl.setList(persistence.findByCriteria((E) this.mapFields.get("entity"),   (String) this.mapFields.get("searchValue")));
			listingControl.setListing(true);
			return true;
		} else {
			subControllerManager.getMessagesControl().addMessageError("Busca");
			return false;
		}
	}
	
	/**
	 * Método que executa a ação de buscar, fazendo as validações e logo depois chama a persitencia
	 * para a ação. É uma busca usando HQL
	 * @param Lista de sub-controladores.
	 */
	public boolean actionFindByHQL(SubControllerManager<E> subControllerManager) {//TODO: reescrever
		if (this.mapFields.get("searchValue") != null && !((String) this.mapFields.get("searchValue")).isEmpty()) {
			ListingControl<E> listingControl = (ListingControl<E>) subControllerManager.getListingControl();
			listingControl.setList(persistence.findByHQL((E) this.mapFields.get("entity"), (String) this.mapFields.get("searchValue")));
			listingControl.setListing(true);
			return true;
		} else {
			subControllerManager.getMessagesControl().addMessageError("Busca");
			return false;
		}
	}
	
	/**
	 * Método que executa a ação de listar, fazendo as validações e logo depois chama a persitencia
	 * para a ação.
	 * @param Lista de sub-controladores.
	 */
	public boolean actionList(SubControllerManager<E> subControllerManager) {
		ListingControl<E> listingControl = (ListingControl<E>) subControllerManager.getListingControl();
		listingControl.setList(persistence.getList((E) this.mapFields.get("entity")));
		listingControl.setListing(true);
		listingControl.setSearch(false);
		return true;
	
	}
	
	/**
	 * Método que executa a ação de Busca, fazendo as validações e logo depois chama a 
	 * persitencia para a ação
	 * @param Lista de subControlers
	 * @return se a ação foi executada
	 */
	public boolean actionSearch(SubControllerManager<E> subControllerManager) {
		if (actionFindByCriteria(subControllerManager)) {
			ListingControl<E> listingControl = (ListingControl<E>) subControllerManager.getListingControl();
			listingControl.setSearchValue((String) this.mapFields.get("searchValue"));
			listingControl.setSearch(true);
			return true;
		} else
			return false;
	}
	
	/**
	 * Método que executa a ação de listar as Foreign Keys, fazendo as validações e logo depois 
	 * chama a persitencia para a ação.
	 * @param Lista de sub-controladores.
	 */
	@SuppressWarnings("rawtypes")
	public boolean listFK(Class entity, SubControllerManager subControllerManager) {
		((ListingControl<E>) subControllerManager.getListingControl()).setListFk(persistence.getListFK(entity));
		return true;
	}
	
	/**
	 * Método que é necessário executar antes de qualquer método dentro do Control
	 * executa as validações e aciona as mensagens caso haja algum erro
	 * @return true caso tudo esteja validado, false caso contrário.
	 */
	public boolean doAnyAction(SubControllerManager<E> subControllersManager, String action) {
		boolean validatorSucess = true;
		List<AbstractValidatorControl> validator = subControllersManager.getValidatorControls(action);			
		for(AbstractValidatorControl vc: validator){
			if(!vc.doAction(this.mapFields, action) && validatorSucess == true)
				validatorSucess = false; 
		}			

		return validatorSucess;
	}
	
	public boolean actionAssociate(SubControllerManager<E> subControllersManager) {
		E selectedEntity = (E) this.mapFields.get("selectedEntity");
		this.gControl.associateEntityToAttributeView(selectedEntity);
		return true;
	}
}