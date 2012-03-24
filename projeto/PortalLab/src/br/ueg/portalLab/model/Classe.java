package br.ueg.portalLab.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ueg.portalLab.util.annotation.Attribute;

/**
 * 
 * @author Tiago
 *
 */
@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "classe")
public class Classe extends Entity {

	@Id
	@GeneratedValue
	@Column(name = "id_classe")
	private long id;
	@Column(name = "nome")
	@Attribute(Required = true, SearchField = true)
	private String classe;
	@ManyToOne(optional = false, targetEntity = Filo.class)
	@JoinColumn(name = "id_filo", referencedColumnName = "id_filo")
	@Attribute(Required = true, SearchField = true)
	private Filo filo;
	
	public Classe() {}
	
	public Classe (long id, String classe, Filo filo) {
		this.id = id;
		this.classe = classe;
		this.filo = filo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public Filo getFilo() {
		return filo;
	}

	public void setFilo(Filo filo) {
		this.filo = filo;
	}

}