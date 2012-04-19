package br.ueg.portalLab.security.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;



/**
 * @author guiliano
 * são os Papeis(roles) do springSecurity
 */
@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "grupo_de_usuario")
public class GrupoUsuario extends Entity  {
	
	@Id
	@GeneratedValue	
	@Column(name = "id_usgr")
	private Long id;	

	@Column(name = "nome_usgr")
	@Attribute(Required = true, SearchField = true)
	private String nome;
		
	@Column(name = "status_usgr")
	@Attribute(Required = true, SearchField = false)
	private Boolean status;
	
	@ManyToMany(cascade =CascadeType.ALL)
	@JoinTable(name="grupo_permissao", 
			joinColumns			={@JoinColumn(name = "id_grup")}, 
			inverseJoinColumns	={@JoinColumn(name = "id_cafu")}
			)
	private Set<CasoDeUsoFuncionalidade> funcionalidades = new HashSet<CasoDeUsoFuncionalidade>(0);
	
	
	public GrupoUsuario(){		
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
