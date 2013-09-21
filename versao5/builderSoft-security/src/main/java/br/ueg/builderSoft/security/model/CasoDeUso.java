package br.ueg.builderSoft.security.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;



/**
 * @author guiliano
 * são os Papeis(roles) do springSecurity
 */
@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "caso_de_uso")
public class CasoDeUso extends Entity  {
	
	@Id
	@GeneratedValue	
	@Column(name = "id_caso")
	private Long id;	

	@Column(name = "nome_caso")
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@Column(name = "descricao_caso")
	@Attribute(Required = true, SearchField = true)
	private String descricao;
		
	@Column(name = "status_caso")
	@Attribute(Required = true, SearchField = false)
	private Boolean status;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casoDeUso",cascade =CascadeType.ALL, orphanRemoval=true )// mappedBy indica o atributo da entidade many
	@Attribute(Required = false, SearchField = false)
	private Set<CasoDeUsoFuncionalidade> funcionalidades = new HashSet<CasoDeUsoFuncionalidade>(0);
	
	
	public CasoDeUso(){		
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
	 * @return the funcionalidades
	 */
	public Set<CasoDeUsoFuncionalidade> getFuncionalidades() {
		return funcionalidades;
	}

	/**
	 * @param funcionalidades the funcionalidades to set
	 */
	public void setFuncionalidades(Set<CasoDeUsoFuncionalidade> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
}
