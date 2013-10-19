package br.ueg.portalLab.model;

import javax.persistence.Column;

import br.ueg.builderSoft.util.annotation.Attribute;

@javax.persistence.Entity
public class EspecieImagem<Image> extends SuperEspecieImagem {

	@Attribute(Required=false, SearchField = true)
	@Column(name="representativa")
	private Boolean representativa = new Boolean(false);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1962064058618011238L;

	@Override
	public String getTypeMediaSimpleName() {
		return "image";
	}

	public Boolean getRepresentativa() {
		return representativa;
	}

	public void setRepresentativa(Boolean representativa) {
		this.representativa = representativa;
	}

}
