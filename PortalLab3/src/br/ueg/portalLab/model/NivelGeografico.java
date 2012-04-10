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
@Table(name = "nivel_geografico")
public class NivelGeografico extends Entity {

	@Id
	@GeneratedValue
	@Column(name = "id_nigeo")
	private long id;
	
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pai_nigeo", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private NivelGeografico pai;
	
	@Column(name = "nome_nigeo", nullable = false, length=20)
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@Column(name = "obrigatorio_nigeo", nullable = false)
	@Attribute(Required = false, SearchField = false)
	private Boolean obrigatorio;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nivelGeografico")// mappedBy indica o atributo da entidade many
	private Set<ItemGeografico> listItensGeograficos;

	public NivelGeografico() {
	}

	public NivelGeografico(long id, String nome, Boolean obrigatorio) {
		this.id = id;
		this.nome = nome;
		this.obrigatorio = obrigatorio;
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
	
	public void setNome(String nome){
		this.nome = nome;
	}	

	public Boolean getObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(Boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}
	
	public NivelGeografico getPai() {
		return pai;
	}

	public void setPai(NivelGeografico pai) {
		this.pai = pai;
	}

	public Set<ItemGeografico> getListItensGeograficos() {
		return this.listItensGeograficos;
	}
	
	public void setListItensGeograficos(Set<ItemGeografico> listItensGeograricos){
		this.listItensGeograficos = listItensGeograricos;
	}
}
