package br.ueg.portalLab.model;


@javax.persistence.Entity
public class FichaTecnica<Image> extends SuperEspecieImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1545857512750696261L;

	/* (non-Javadoc)
	 * @see br.ueg.portalLab.model.EspecieMultimidia#getTypeMediaSimpleName()
	 */
	@Override
	public String getTypeMediaSimpleName() {
		return "ficha_tecnica";
	}
	
	

}
