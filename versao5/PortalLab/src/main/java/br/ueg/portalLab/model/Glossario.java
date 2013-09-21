package br.ueg.portalLab.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name="glossario")
public class Glossario extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_item")
	private Long id;
	
	@Column(name = "nome", length = 50)
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@Column(name = "descricao", length = 1000)
	@Attribute(Required = true, SearchField = false)
	private String descricao;
	
	
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

	public String getDescricao(){
		return this.descricao;
	}
	
	public void setDescricao(String vDescricao){
		this.descricao = vDescricao;
	}
	
	@Override
	public String toString(){
		return this.getDescricao();
	}
	
	@Override
	protected String getColumnCompare(){
		return String.valueOf(getDescricao());
	}

}
