package br.ueg.portalLab.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

@javax.persistence.Entity
@SuppressWarnings("serial")
@Table(name="integrante_equipe")
public class IntegranteEquipe extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_integrante_equipe")
	private Long id;
	
	@Column(name = "nome")
	@Attribute(Required = true, SearchField = true)
	private String nome;
		
	
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

}
