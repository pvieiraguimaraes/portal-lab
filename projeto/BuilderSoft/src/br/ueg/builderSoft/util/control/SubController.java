package br.ueg.builderSoft.util.control;

import java.util.HashMap;

/**
 * Interface dos subControladores
 * @author Diego
 *
 */
public interface SubController {
	
	/**
	 * Método que executa a ação do subControlador.
	 * @param mapFields
	 * @param action
	 * @return boolean que indica se a ação foi executada com sucesso
	 */
	public boolean doAction(HashMap<String, Object> mapFields, String action);

}
