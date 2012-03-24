package br.ueg.builderSoft.view.managed;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;

import br.ueg.builderSoft.control.GenericControl;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.constant.ControllerType;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.util.reflection.Reflection;
import br.ueg.builderSoft.view.control.MessagesWeb;

/**
 * ManagedBean gen�rico, contendo os m�todos comuns, deve ser herdado por todos
 * outros ManagedBeans
 * @author Diego
 *
 * @param <Elemento que extenda Entity do modelo>
 */
public abstract class MB<E extends Entity> {
	
	protected GenericControl<E> control;
	@AttributeView(key = "id", isEntityValue = true, entityType = Long.class, isVisible=false, caption="mb_idColumn")
	protected long fldId;
	protected List<E> listEntity;
	@AttributeView(key = "searchValue")
	protected String fldBusca = "";
	protected ListingControl<E> listingControl;
	@AttributeView(key = "selectedEntity")
	protected E selectedEntity;
	
	@SuppressWarnings("unchecked")
	public MB() {
		control = new GenericControl<E>(new MessagesWeb(), new ListingControl<E>(), this);
		listingControl = (ListingControl<E>) control.getController(ControllerType.LISTING);
		//initializeEntity();
	}

	//Segue getters and setters
	/**
	 * @return o controlador
	 */
	protected GenericControl<E> getControl() {
		return control;
	}

	/**
	 * seta o controlador
	 * @param control
	 */
	protected void setControl(GenericControl<E> control) {
		this.control = control;
	}

	/**
	 * @return the fldId
	 */
	public long getFldId() {
		return fldId;
	}

	/**
	 * @param fldId the fldId to set
	 */
	public void setFldId(long fldId) {
		this.fldId = fldId;
	}

	/**
	 * @return the fldBusca
	 */
	public String getFldBusca() {
		return fldBusca;
	}

	/**
	 * @param fldBusca the fldBusca to set
	 */
	public void setFldBusca(String fldBusca) {
		this.fldBusca = fldBusca;
	}

	public List<E> getListEntity() {
		return listEntity;
	}

	public void setListEntity(List<E> listEntity) {
		this.listEntity = listEntity;
	}

	/**
	 * @param listingControl the listingControl to set
	 */
	public void setListingControl(ListingControl<E> listingControl) {
		this.listingControl = listingControl;
	}

	/**
	 * @return the listingControl
	 */
	public ListingControl<E> getListingControl() {
		return listingControl;
	}
	//Fim dos getters and Setters
	
	/**
	 * @return the selectedEntity
	 */
	public E getSelectedEntity() {
		return selectedEntity;
	}

	/**
	 * @param selectedEntity the selectedEntity to set
	 */
	public void setSelectedEntity(E selectedEntity) {
		this.selectedEntity = selectedEntity;
	}

	/**
	 * M�todo respons�vel por receber a "action" dos bot�es, chamando o controle para 
	 * execut�-la
	 * @param ActionEvent
	 */
	public void doAction(ActionEvent e) {
		if (control.doAction(e.getComponent().getId(), initializeEntity())) {
			verifyListing(e.getComponent().getId());
		}
	}
	
	/**
	 * M�todo que verifica no ListingControl se foi feito uma listagem e atualiza/seta
	 * a lista da vis�o
	 */
	protected void verifyListing(String action) {
		
			if (this.isListing()) {
				listEntity = listingControl.getList();
			}
	}
	
	/**
	 * M�todo que verifica se j� h� uma listagem ou n�o
	 * @return true se h� uma listagem, ou false caso contr�rio
	 */
	public boolean isListing() {
		return listingControl.doAction(null, null);
	}
	
	/**
	 * M�todo para os bot�es que inicia uma nova vari�vel
	 * @param event
	 */
	public void newEntity(ActionEvent event) {
		this.control.associateEntityToAttributeView(this.initializeEntity());
	}
	
	/**
	 * M�todo para cancelar uma a��o
	 * @param event
	 */
	public void cancelAction(ActionEvent event) {
		this.control.associateEntityToAttributeView(this.initializeEntity());
		//verifyListing();
	}
	
	/*public List<String> getSearchFields() {
		return listingControl.getSearchFields();
	}*/
	
	/*M�todo dinamico de constru��o do SelectOne -- n�o funciona
	 * public HtmlSelectOneListbox selectSearch() {
		HtmlSelectOneListbox select = new HtmlSelectOneListbox();
		select.setId("select" + entity.toString());
		select.setSize(1);
		select.setValue(valueSelected);
		
		SelectItem selectItem = new SelectItem();
		selectItem.setLabel("");
		selectItem.setValue(null);
		
		
		
		select.getChildren().add(selectItem);
		return select;
	}*/
	
	/**
	 * Met�do que inicializa a entidade
	 */
	protected abstract E initializeEntity();
	
	//TODO Criar 2 listas, uma de fk, um de entidade, sendo a fk pega por reflection e feito 
	//seu casting
	
	public List<ManagedBeanField> getListColumns() {
		List<ManagedBeanField> list = new ArrayList<ManagedBeanField>();
		
		for (Class<?> reflectedClass = this.getClass(); reflectedClass != null; reflectedClass = reflectedClass.getSuperclass()) {
			Field[] fields = reflectedClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class) != null) {
					try {
						//Object fieldValue = Reflection.getFieldValue(this, field.getName());
						String vKey = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).key();
						String fieldName = field.getName();
						//Class fieldType = field.getAnnotation(br.ueg.portalLab.util.annotation.AttributeView.class).entityType();
						String vCaption = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).caption();
						String vEntityType = Reflection.getClassName(field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).entityType());
						boolean vIsVisible = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).isVisible();
						int vComponent = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).component();
						if (field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).isEntityValue()) {
							list.add(new ManagedBeanField(vKey, fieldName, vCaption,vEntityType, vIsVisible, vComponent));
						}
					
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					
					}
				}
			}
		}
		
		return list;
	}
	
	
	

}
