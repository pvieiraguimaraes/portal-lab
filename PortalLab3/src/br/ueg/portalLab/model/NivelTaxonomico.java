package br.ueg.portalLab.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

/**
 * Classe utilizada para modelar os níveis geográficos(pais, estado, cidade, localidade), mas pode iniciar até com o nível continente se desjar
 * é um exemplo de controle com hierarquia.
 * @author Diego
 *
 */
@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "nivel_taxonomico")
public class NivelTaxonomico extends Entity {

	@Id
	@GeneratedValue
	@Column(name = "id_nitax")
	private Long id;
	
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pai_nitax", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private NivelTaxonomico pai;
	
	@Column(name = "nome_nitax", nullable = false, length=20)
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@Column(name = "obrigatorio_nitax", nullable = false)
	@Attribute(Required = false, SearchField = false)
	private Boolean obrigatorio;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pai")// mappedBy indica o atributo da entidade many
	private Set<NivelTaxonomico> listNivelTaxonomico;

	public NivelTaxonomico() {
	}

	public NivelTaxonomico(long id, String nome, Boolean obrigatorio) {
		this.id = id;
		this.nome = nome;
		this.obrigatorio = obrigatorio;
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
	
	public void setNome(String nome){
		this.nome = nome;
	}	

	public Boolean getObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(Boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}
	
	public NivelTaxonomico getPai() {
		return pai;
	}

	public void setPai(NivelTaxonomico pai) {
		this.pai = pai;
	}

	public Set<NivelTaxonomico> getListNivelTaxonomico() {
		return this.listNivelTaxonomico;
	}
	
	public void setListNivelTaxonomico(Set<NivelTaxonomico> listNivelTaxonomico){
		this.listNivelTaxonomico = listNivelTaxonomico;
	}
}
