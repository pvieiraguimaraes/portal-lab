package br.ueg.builderSoft.util.control;

import java.util.List;

import br.ueg.builderSoft.model.Entity;

/**
 * Interface que controla se há uma listagem na visão ou não
 * @author Diego
 *
 */
@SuppressWarnings("rawtypes")
public interface IListingControl <E extends Entity> extends SubController{

	/**
	 * Método que seta o parâmetro booleano de listagem
	 * @param listing the listing to set
	 */
	public void setListing(boolean listing);

	/**
	 * Método que seta a lista de entidades
	 * @param list the list to set
	 */
	public void setList(List<E> list);

	/**
	 * Método que retorna a lista de entidades
	 * @return the list
	 */
	public List<E> getList();

	/**
	 * Método que seta a lista de Foreign Key
	 * @param listFk the listFk to set
	 */
	public void setListFk(List listFk);

	/**
	 * Método que retorna a lista de Foreign Key
	 * @return the listFk
	 */
	public List getListFk();

	/** 
	 * @return
	 * Retorna o valor a ser feito a busca
	 */
	public Object getSearchValue();

	/**
	 * Indica se o control está litando.
	 * @return boolean indica se está em listagem
	 */
	public boolean isSearch();
}
