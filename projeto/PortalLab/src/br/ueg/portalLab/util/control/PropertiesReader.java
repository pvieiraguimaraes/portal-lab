package br.ueg.portalLab.util.control;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import br.ueg.portalLab.model.Entity;

/**
 * Classe que lê arquivos .properties
 * @author Diego
 *
 */
public class PropertiesReader {
	
	private ResourceBundle bundle;
	
	public PropertiesReader(String localBundle) {
		this.bundle = ResourceBundle.getBundle(localBundle);
	}
	
	/**
	 * Método que retorna os searchField dentro do bundle iniciado com o nome da Entidade
	 * @param entity
	 * @return Lista de nomes dos campos de busca
	 */
	public List<String> getSearchFields(Entity entity) {
		List<String> searchFields = new ArrayList<String>();
		for (int i = 0; i != -1; i++) {
			try {
				searchFields.add(this.bundle.getString(entity.getClass().getSimpleName().toLowerCase() + "_" + i));
			} catch (MissingResourceException e) {
				break;
			}
		}
		return searchFields;
	}

}