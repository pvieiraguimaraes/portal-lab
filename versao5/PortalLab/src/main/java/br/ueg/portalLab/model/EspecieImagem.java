package br.ueg.portalLab.model;

@javax.persistence.Entity
public class EspecieImagem<Image> extends SuperEspecieImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1962064058618011238L;

	@Override
	public String getTypeMediaSimpleName() {
		return "image";
	}

}
