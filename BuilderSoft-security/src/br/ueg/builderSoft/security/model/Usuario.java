package br.ueg.builderSoft.security.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;



@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "usuarios")
public class Usuario extends Entity  {
	
	@Id
	@Column(name = "codigo_usua")
	private long id;	

	@Column(name = "login_usua")
	@Attribute(Required = true, SearchField = true)
	private String login;
	
	@Column(name = "senha_usua")
	@Attribute(Required = true, SearchField = false)
	private String senha;
	
	@Column(name = "status_usua")
	@Attribute(Required = true, SearchField = false)
	private String status;
	
	@Column(name = "nome_usua")
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	public Usuario(){		
	}
			
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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

}
