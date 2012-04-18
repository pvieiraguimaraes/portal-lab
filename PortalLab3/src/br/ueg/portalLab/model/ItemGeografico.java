package br.ueg.portalLab.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

/**
 * Classe Teste que contém relacionamento com Reino.
 * @author Diego
 *
 */
@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "item_geografico")
public class ItemGeografico extends Entity {

	@Id
	@GeneratedValue
	@Column(name = "id_itgeo")
	private Long id;
	
	@Column(name = "nome_itgeo")
	@Attribute(Required = true, SearchField = true)
	@OrderBy("nome")
	private String nome;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nigeo_itgeo", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = true, SearchField = true)
	private NivelGeografico nivelGeografico;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pai_itgeo", insertable = true, updatable = true, nullable=true)
	private ItemGeografico pai; 
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pai",cascade = CascadeType.ALL)// mappedBy indica o atributo da entidade many	
	private Set<ItemGeografico> filhosItensGeograficos;
	
	public ItemGeografico() {}
	

	public ItemGeografico(long id, String nome,
			NivelGeografico nivelGeografico, ItemGeografico pai) {
		this.id = id;
		this.nome = nome;
		this.nivelGeografico = nivelGeografico;
		this.pai = pai;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public NivelGeografico getNivelGeografico() {
		return nivelGeografico;
	}


	public void setNivelGeografico(NivelGeografico nivelGeografico) {
		this.nivelGeografico = nivelGeografico;
	}


	public ItemGeografico getPai() {
		return pai;
	}


	public void setPai(ItemGeografico pai) {
		this.pai = pai;
	}


	public Set<ItemGeografico> getFilhosItensGeograficos() {
		return filhosItensGeograficos;
	}


	public void setFilhosItensGeograficos(Set<ItemGeografico> filhosItensGeograficos) {
		this.filhosItensGeograficos = filhosItensGeograficos;
	}

}