package br.ueg.portalLab.util.control;

import java.util.List;

/**
 * Interface dos subControladores
 * @author Diego
 *
 */
public interface SubController {
	
	/**
	 * Método que executa a ação do subControlador.
	 * @param entity
	 * @param action
	 * @return 
	 */
	public boolean doAction(List<Object> atributes, String action);

}
