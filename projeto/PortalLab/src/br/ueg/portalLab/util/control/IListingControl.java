package br.ueg.portalLab.util.control;

import java.util.List;

import br.ueg.portalLab.model.Entity;

/**
 * Interface que controla se h� uma listagem na vis�o ou n�o
 * @author Diego
 *
 */
@SuppressWarnings("rawtypes")
public interface IListingControl <E extends Entity> extends SubController{

	/**
	 * M�todo que seta o par�metro booleano de listagem
	 * @param listing the listing to set
	 */
	public void setListing(boolean listing);

	/**
	 * M�todo que seta a lista de entidades
	 * @param list the list to set
	 */
	public void setList(List<E> list);

	/**
	 * M�todo que retorna a lista de entidades
	 * @return the list
	 */
	public List<E> getList();

	/**
	 * M�todo que seta a lista de Foreign Key
	 * @param listFk the listFk to set
	 */
	public void setListFk(List listFk);

	/**
	 * M�todo que retorna a lista de Foreign Key
	 * @return the listFk
	 */
	public List getListFk();
}
