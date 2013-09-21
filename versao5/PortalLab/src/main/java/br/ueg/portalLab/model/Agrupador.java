package br.ueg.portalLab.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name="agrupador")
public class Agrupador extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_agrupador")
	private Long id;
	
	@Column(name = "nome_agrupador")
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="agrupador", targetEntity=AgrupadorItensTaxonomicos.class)
	private Set<AgrupadorItensTaxonomicos> itens;
	
	@Override
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

	public Set<AgrupadorItensTaxonomicos> getItens() {
		return itens;
	}

	public void setItens(Set<AgrupadorItensTaxonomicos> itens) {
		this.itens = itens;
	}

}
