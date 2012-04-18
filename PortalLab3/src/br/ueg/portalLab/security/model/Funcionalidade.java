package br.ueg.portalLab.security.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;



/**
 * @author guiliano
 * são os Papeis(roles) do springSecurity
 */
@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "funcionalidade")
public class Funcionalidade extends Entity  {
	
	@Id
	@GeneratedValue	
	@Column(name = "id_func")
	private Long id;	

	@Column(name = "nome_func")
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@Column(name = "descricao_func")
	@Attribute(Required = true, SearchField = true)
	private String descricao;
		
	@Column(name = "status_func")
	@Attribute(Required = true, SearchField = false)
	private Boolean status;
	
	
	public Funcionalidade(){		
	}
			
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString(){
		return this.getNome()+" ("+this.getDescricao()+" )";
	}
}
