package br.ueg.builderSoft.control;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.model.interfaces.AttributesMedia;
import br.ueg.builderSoft.persistence.DataIntegrityViolationException;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.Utils;
import br.ueg.builderSoft.util.annotation.Image;
import br.ueg.builderSoft.util.annotation.Media;
import br.ueg.builderSoft.util.control.AbstractValidatorControl;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.control.ValidatorControl;
import br.ueg.builderSoft.util.medias.Images;
import br.ueg.builderSoft.util.medias.Medias;
import br.ueg.builderSoft.util.reflection.Reflection;
import br.ueg.builderSoft.util.sets.Config;
import br.ueg.builderSoft.util.sets.SpringFactory;

@SuppressWarnings("unchecked")
@Service
public class Control<E extends Entity>{
	

	protected GenericDAO<E> persistence;
	protected GenericDAO<E> getPersistence() {
		if(persistence==null){
			this.persistence = (GenericDAO<E>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		}
		return persistence;
	}



	protected HashMap<String, Object> mapFields;
	private MessagesControl messagesControl;
	//construtores do subcontroller mandaram algo +
	//controlador principal para fazer modificaçães
	//DUVIDA: criar todos validadores para chamar seu método principal no construtor
	//e mandar a instancia desse control pra ele setar o booleano continue
	//tenod que verificar esse a todo momento
	//assim no control terei um for para percorrer a lista de subcontrolers
	//para usar todos.
	
	/*
	 * Criar classe que extenda control, e dentro do construtor do filho da super do filho
	 * e aumentar no arraylist de validaçães.
	 */
	public Control(MessagesControl pMessagesControl) {
		//this.persistence = (GenericDAO<E>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		this.messagesControl = pMessagesControl;
	}
	public Control(){
		
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
	 * Metodo utilizado para buscar um lista de entidade baseado no tipo da entidade passada
	 * @param entity
	 * @return retorna uma lista de entity 
	 */
	@SuppressWarnings("rawtypes")
	public List<Entity> getListFKEntity(Class entity){
		GenericDAO<Entity> genericDAO = SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);		
		return genericDAO.getList(entity);		
	}
	
	/**
	 * Metodo utilizado para buscar uma lista de entidade baseado no tipo da entidade passada
	 * usando o searchValue para filtrar os resultados
	 * @param entity
	 * @param searchValue
	 * @return List<Entity>
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
	
	@SuppressWarnings("rawtypes")
	public HashMap<String, Object> getMapFields(){
		if(mapFields == null){
			mapFields = new HashMap();
		}
		return this.mapFields;
	}

	/**
	 * Método que executa a ação de incluir, fazendo as validaçães e logo depois chama a persitencia
	 * para salvar, caso seja a ação de atualizar, chama o método responsável para isso.
	 * @param subControllerManager
	 * @return boolean indica se ação foi realizada com sucesso
	 */
	public boolean actionSave(SubControllerManager<E> subControllerManager) {
		E entity = (E) this.mapFields.get("entity");
		if (entity.isNew()) {
			boolean result = false;
			try{
				if (getPersistence().save(entity) != 0) {
					result = saveMedias(entity);
				} 
			}catch(DataIntegrityViolationException e){
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
	
	@SuppressWarnings("resource")
	private Boolean saveMedias(E entity){
		Boolean flag = true;
		if(entity instanceof AttributesMedia){
			AttributesMedia en = (AttributesMedia) entity;
			for(Field f : Reflection.getAnnotatedFields(entity, Media.class)){
				try {
					f.setAccessible(true);
					Object obj = f.get(entity);
					Media mediaAnnot = f.getAnnotation(Media.class);
					if(obj != null){
						InputStream media = (InputStream) obj;
						if(f.getAnnotation(Image.class) != null){
							media = prepareImage(media, f.getAnnotation(Image.class));					
							InputStream thumb = createThumb(media, f.getAnnotation(Image.class));
							if(thumb != null){
								flag = Medias.writeInputStreamToDisk(en.getBasePath() + en.getRelativePath() + mediaAnnot.value() + "_thumb.png", thumb);
							}
						}

						flag = Medias.writeInputStreamToDisk(en.getBasePath() + en.getRelativePath() + mediaAnnot.value() + ".png", media);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
				} 
			}
		}
		return flag;
	}
	
	private InputStream prepareImage(InputStream image, Image annot) throws IOException{
		Config conf = new Config();
		
		Integer width;
		Integer height;
		
		if(Utils.isInteger(annot.width())){
			width = Integer.parseInt(annot.width());
		} else {
			width = Integer.parseInt(conf.getKey(annot.width()));
		}
		
		if(Utils.isInteger(annot.height())){
			height = Integer.parseInt(annot.height());
		} else {
			height = Integer.parseInt(conf.getKey(annot.height()));
		}
		
		if(width != 0 && height != 0){
			return Images.scaleImage(image, width, height);
		} else{
			return Images.convertToPNG(image);
		}
		
	}
	
	private InputStream createThumb(InputStream image, Image annot) throws IOException{
		Config conf = new Config();
		
		InputStream thumb = null;
		
		if(annot.thumb() && !annot.thumbHeight().isEmpty() && !annot.thumbWidth().isEmpty()){
			Integer twidth;
			Integer theight;
			
			if(Utils.isInteger(annot.thumbWidth())){
				twidth = Integer.parseInt(annot.thumbWidth());
			} else {
				twidth = Integer.parseInt(conf.getKey(annot.thumbWidth()));
			}
			
			if(Utils.isInteger(annot.thumbHeight())){
				theight = Integer.parseInt(annot.thumbHeight());
			} else {
				theight = Integer.parseInt(conf.getKey(annot.thumbHeight()));
			}
			
			thumb = Images.scaleImage(image, twidth, theight);
		}
		return thumb;
	}
	
	/**
	 * Método que executa a ação de atualizar, fazendo as validaçães e logo depois chama a persitencia
	 * para a ação.
	 * @param subControllerManager
	 * @return boolean indica se a ação foi executada com sucesso.
	 */
	protected boolean actionUpdate(SubControllerManager<E> subControllerManager) {
		E entity = (E) this.mapFields.get("entity");

		boolean result = false;
		try{
			getPersistence().update(entity);
			result = true;			
		}catch(DataIntegrityViolationException e){
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
	 * Método que executa a ação de deletar, fazendo as validaçães e logo depois chama a persitencia
	 * para a ação.
	 * @param subControllerManager
	 * @return boolean indica se ação foi executada com sucesso
	 */
	public boolean actionDelete(SubControllerManager<E> subControllerManager) {
		try {
			getPersistence().delete((E) this.mapFields.get("selectedEntity"));
			return true;
		}  catch (Exception e) {
			e.printStackTrace();
			subControllerManager.getMessagesControl().addMessageError("CantDelete");
			return false;
		}
	}
	
	/**
	 * Método que executa a ação de buscar, fazendo as validaçães e logo depois chama a persitencia
	 * para a ação. é uma busca usando Criteria
	 * @param subControllerManager
	 * @return boolean indica se ação foi realizada com sucesso.
	 */
	public boolean actionFindByCriteria(SubControllerManager<E> subControllerManager) {//TODO: reescrever
		if ( this.mapFields.get("searchValue") != null && ! ((String) this.mapFields.get("searchValue")).isEmpty()) {
			ListingControl<E> listingControl = (ListingControl<E>) subControllerManager.getListingControl();
			listingControl.setList(getPersistence().findByCriteria((E) this.mapFields.get("entity"),   (String) this.mapFields.get("searchValue")));
			listingControl.setListing(true);
			return true;
		} else {
			subControllerManager.getMessagesControl().addMessageError("Busca");
			return false;
		}
	}
	
	/**
	 * Método que executa a ação de buscar, fazendo as validaçães e logo depois chama a persitencia
	 * para a ação. é uma busca usando HQL
	 * @param subControllerManager
	 * @return boolean indica se ação foi realizada com sucesso
	 */
	public boolean actionFindByHQL(SubControllerManager<E> subControllerManager) {//TODO: reescrever
		if (this.mapFields.get("searchValue") != null && !((String) this.mapFields.get("searchValue")).isEmpty()) {
			ListingControl<E> listingControl = (ListingControl<E>) subControllerManager.getListingControl();
			listingControl.setList(getPersistence().findByHQL((E) this.mapFields.get("entity"), (String) this.mapFields.get("searchValue")));
			listingControl.setListing(true);
			return true;
		} else {
			subControllerManager.getMessagesControl().addMessageError("Busca");
			return false;
		}
	}
	
	/**
	 * Método que executa a ação de listar, fazendo as validaçães e logo depois chama a persitencia
	 * para a ação.
	 * @param subControllerManager
	 * @return boolean indica se ação foi realizada com sucesso
	 */
	public boolean actionList(SubControllerManager<E> subControllerManager) {
		ListingControl<E> listingControl = (ListingControl<E>) subControllerManager.getListingControl();
		listingControl.setList(getPersistence().getList((E) this.mapFields.get("entity")));
		listingControl.setListing(true);
		listingControl.setSearch(false);
		return true;
	
	}
	
	/**
	 * Método que executa a ação de Busca, fazendo as validaçães e logo depois chama a 
	 * persitencia para a ação
	 * @param subControllerManager Lista de subControlers
	 * @return boolean se a ação foi executada
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
	 * Método que executa a ação de listar as Foreign Keys, fazendo as validaçães e logo depois 
	 * chama a persitencia para a ação.
	 * @param entity
	 * @param subControllerManager
	 * @return boolean indica se a ação foi realizada com sucesso
	 */
	@SuppressWarnings("rawtypes")
	public boolean listFK(Class entity, SubControllerManager subControllerManager) {
		((ListingControl<E>) subControllerManager.getListingControl()).setListFk(persistence.getListFK(entity));
		return true;
	}
	
	/**
	 * Método que é necessário executar antes de qualquer método dentro do Control
	 * executa as validaçães e aciona as mensagens caso haja algum erro
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
//		E selectedEntity = (E) this.mapFields.get("selectedEntity");
//		this.genericControl.associateEntityToAttributeView(selectedEntity);
		return true;
	}
	
	public boolean actionCanceledit(SubControllerManager<E> subControllersManager){
		this.getPersistence().refresh((E) this.mapFields.get("entity"));
		return true;
	}
	
	public List<SubController> getListValidator(){
		ArrayList<SubController> list = new ArrayList<SubController>();
		list.add((SubController)new ValidatorControl(this.getMessagesControl(),0,Arrays.asList("SAVE")));
		return list;
	}



	/**
	 * @return the messagesControl
	 */
	public MessagesControl getMessagesControl() {
		if(messagesControl==null){
			this.messagesControl = (MessagesControl) SpringFactory.getInstance().getBean("messagesControl", MessagesControl.class);
		}
		return messagesControl;
	}



	/**
	 * @param messagesControl the messagesControl to set
	 */
	public void setMessagesControl(MessagesControl messagesControl) {
		this.messagesControl = messagesControl;
	}
}