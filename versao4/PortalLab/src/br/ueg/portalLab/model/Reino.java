package br.ueg.portalLab.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

/**
 * Classe exemplo
 * @author Diego
 *
 */
@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "reino")
public class Reino extends Entity {

	@Id
	@GeneratedValue
	@OneToMany(targetEntity = Filo.class, mappedBy = "reino")
	@Column(name = "id_reino")
	private Long id;
	@Column(name = "nome")
	@Attribute(Required = true, SearchField = true)
	private String reino;

	public Reino() {
	}

	public Reino(long id, String reino) {
		this.id = id;
		this.reino = reino;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReino() {
		return reino;
	}

	public void setReino(String reino) {
		this.reino = reino;
	}

}
