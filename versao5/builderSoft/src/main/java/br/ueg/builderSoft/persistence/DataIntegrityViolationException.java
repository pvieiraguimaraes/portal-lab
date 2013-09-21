package br.ueg.builderSoft.persistence;

/**
 * @author guiliano
 * Classe utilizada para tratamento de erro gerado pelo banco de dados.
 */
public class DataIntegrityViolationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1945256385391272637L;

	public DataIntegrityViolationException(String message) {
		super(message);
		
	}
	
	

}
