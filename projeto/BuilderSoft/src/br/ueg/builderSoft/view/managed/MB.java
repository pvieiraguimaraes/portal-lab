package br.ueg.builderSoft.view.managed;

import javax.faces.event.ActionEvent;

import br.ueg.builderSoft.control.GenericControl;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.constant.ControllerType;
import br.ueg.builderSoft.util.control.IListingControl;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.view.control.MessagesWeb;

/**
 * ManagedBean gen�rico, contendo os m�todos comuns, deve ser herdado por todos
 * outros ManagedBeans
 * @author Diego
 *
 * @param <Elemento que extenda Entity do modelo>
 */
public abstract class MB<E extends Entity> extends GenericMB<E> {
	
	@SuppressWarnings("unchecked")
	public MB() {
		control = new GenericControl<E>(new MessagesWeb(), new ListingControl<E>(), this);
		listingControl = (ListingControl<E>) control.getController(IListingControl.class);
		//initializeEntity();
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
	
	//TODO Criar 2 listas, uma de fk, um de entidade, sendo a fk pega por reflection e feito 
	//seu casting

	
	
	

}
