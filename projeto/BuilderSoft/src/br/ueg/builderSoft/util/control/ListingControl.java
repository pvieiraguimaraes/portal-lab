package br.ueg.builderSoft.util.control;

import java.util.HashMap;
import java.util.List;

import br.ueg.builderSoft.model.Entity;

/**
 * Classe que é responsável por responder se na visão está sendo listado
 * a entidade ou não.
 * 
 * @author Diego
 *
 */
@SuppressWarnings("rawtypes")
public class ListingControl<E extends Entity> implements IListingControl<E>{
	
	private boolean listing;
	private boolean search = false;
	private String searchValue = "";
	private List<E> list = null;
	private List listFk = null;
	private PropertiesReader propertiesReader;
	
	public ListingControl() {
		setListing(false);
		this.propertiesReader = new PropertiesReader("br/ueg/builderSoft/config/searchFields");
	}

	@Override
	public void setListing(boolean listing) {
		this.listing = listing;
	}

	/**
	 * @return the search
	 */
	public boolean isSearch() {
		return search;
	}

	/**
	 * @param search the search to set
	 */
	public void setSearch(boolean search) {
		this.search = search;
	}

	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * @param searchValue the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	@Override
	public void setList(List<E> list) {
		this.list = list;
	}

	@Override
	public List<E> getList() {
		return this.list;
	}

	@Override
	public void setListFk(List listFk) {
		this.listFk = listFk;
	}

	@Override
	public List getListFk() {
		return this.listFk;
	}
	
	/**
	 * Método irá buscar a lista de campos de busca para preencher a combo box
	 * @param entity
	 * @return Lista de campos de buscas
	 */
	public List<String> getSearchFields(E entity) {
		return this.propertiesReader.getSearchFields(entity);
	}

	@Override
	public boolean doAction(HashMap<String, Object> mapFields, String action) {
		return this.listing;
	}

}
