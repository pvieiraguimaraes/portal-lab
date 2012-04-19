package br.ueg.builderSoft.security.model;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;


import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;



@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "usuarios")
public class Usuario extends Entity  {
	
	@Id
	@GeneratedValue	
	@Column(name = "codigo_usua")
	private Long id;	

	@Column(name = "login_usua", length=25, nullable=false)
	@Attribute(Required = true, SearchField = true)
	private String login;
	
	@Column(name = "senha_usua", length=15, nullable=false)
	@Attribute(Required = true, SearchField = false)
	private String senha;
	
	@Column(name = "status_usua", length=20, nullable=false, columnDefinition="varchar(20) default 'Ativo'")
	@Attribute(Required = true, SearchField = false)
	private String status;
	
	@Column(name = "nome_usua", length=80, nullable=false)
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@ManyToMany(cascade =CascadeType.ALL)
	@JoinTable(name="usuario_grupo", 
			joinColumns			={@JoinColumn(name = "id_usua_usgr")}, 
			inverseJoinColumns	={@JoinColumn(name = "id_grus_usgr")}
			)
	private Set<GrupoUsuario> grupos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario",cascade = CascadeType.ALL)// mappedBy indica o atributo da entidade many
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@Attribute(Required = false, SearchField = false)
	private Set<UsuarioPermissao> permissoes = new HashSet<UsuarioPermissao>(0);
	
	public Usuario(){		
	}
			
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	/**
	 * @return the grupos
	 */
	public Set<GrupoUsuario> getGrupos() {
		return grupos;
	}

	/**
	 * @param grupos the grupos to set
	 */
	public void setGrupos(Set<GrupoUsuario> grupos) {
		this.grupos = grupos;
	}

	/**
	 * @return the permissoes
	 */
	public Set<UsuarioPermissao> getPermissoes() {
		return permissoes;
	}

	/**
	 * @param permissoes the permissoes to set
	 */
	public void setPermissoes(Set<UsuarioPermissao> permissoes) {
		this.permissoes = permissoes;
	}
}
