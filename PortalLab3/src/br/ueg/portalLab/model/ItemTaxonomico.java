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
@Table(name = "item_taxonomico")
public class ItemTaxonomico extends Entity {

	@Id
	@GeneratedValue
	@Column(name = "id_ittax")
	private long id;
	
	@Column(name = "nome_ittax")
	@Attribute(Required = true, SearchField = true)
	@OrderBy("nome")
	private String nome;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nigeo_ittax", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = true, SearchField = true)
	private NivelTaxonomico nivelTaxonomico;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pai_ittax", insertable = true, updatable = true, nullable=true)
	private ItemTaxonomico pai; 
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pai",cascade = CascadeType.ALL)// mappedBy indica o atributo da entidade many	
	private Set<ItemTaxonomico> filhosItensTaxonomicos;
	
	public ItemTaxonomico() {}
	

	public ItemTaxonomico(long id, String nome,
			NivelTaxonomico nivelTaxonomico, ItemTaxonomico pai) {
		this.id = id;
		this.nome = nome;
		this.nivelTaxonomico = nivelTaxonomico;
		this.pai = pai;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public NivelTaxonomico getNivelTaxonomico() {
		return nivelTaxonomico;
	}


	public void setNivelTaxonomico(NivelTaxonomico nivelTaxonomico) {
		this.nivelTaxonomico = nivelTaxonomico;
	}


	public ItemTaxonomico getPai() {
		return pai;
	}


	public void setPai(ItemTaxonomico pai) {
		this.pai = pai;
	}


	public Set<ItemTaxonomico> getFilhosItensTaxonomicos() {
		return filhosItensTaxonomicos;
	}


	public void setFilhosItensTaxonomicos(Set<ItemTaxonomico> filhosItensTaxonomicos) {
		this.filhosItensTaxonomicos = filhosItensTaxonomicos;
	}
	
	@Override
	public String toString(){
		return this.getNivelTaxonomico().getNome()+" "+this.getNome();
	}

}