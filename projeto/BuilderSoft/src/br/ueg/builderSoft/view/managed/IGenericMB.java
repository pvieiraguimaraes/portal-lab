package br.ueg.builderSoft.view.managed;

import java.util.List;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.control.ListingControl;

public interface IGenericMB<E extends Entity> {

	/**
	 * @return the fldId
	 */
	public abstract long getFldId();

	/**
	 * @param fldId the fldId to set
	 */
	public abstract void setFldId(long fldId);

	/**
	 * @return the fldBusca
	 */
	public abstract String getFldBusca();

	/**
	 * @param fldBusca the fldBusca to set
	 */
	public abstract void setFldBusca(String fldBusca);

	public abstract List<E> getListEntity();

	public abstract void setListEntity(List<E> listEntity);

	/**
	 * @param listingControl the listingControl to set
	 */
	public abstract void setListingControl(ListingControl<E> listingControl);

	/**
	 * @return the listingControl
	 */
	public abstract ListingControl<E> getListingControl();

	//Fim dos getters and Setters

	/**
	 * @return the selectedEntity
	 */
	public abstract E getSelectedEntity();

	/**
	 * @param selectedEntity the selectedEntity to set
	 */
	public abstract void setSelectedEntity(E selectedEntity);

	/**
	 * Método que verifica se já há uma listagem ou não
	 * @return true se há uma listagem, ou false caso contrário
	 */
	public abstract boolean isListing();

	public abstract List<ManagedBeanField> getListColumns();

	@SuppressWarnings("rawtypes")
	public abstract Class getEntityClass();

}