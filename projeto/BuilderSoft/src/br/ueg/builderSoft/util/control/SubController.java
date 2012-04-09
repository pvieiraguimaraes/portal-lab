package br.ueg.builderSoft.util.control;

import java.util.HashMap;

/**
 * Interface dos subControladores
 * @author Diego
 *
 */
public interface SubController {
	
	/**
	 * M�todo que executa a a��o do subControlador.
	 * @param entity
	 * @param action
	 * @return 
	 */
	public boolean doAction(HashMap<String, Object> mapFields, String action);

}
