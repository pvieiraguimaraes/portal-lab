package br.ueg.portalLab.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;



import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.portalLab.security.model.GrupoUsuario;
import br.ueg.portalLab.security.model.UsuarioPermissao;

@javax.persistence.Entity
@SuppressWarnings("serial")
@Table(name="usuario")
public class Usuario extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_usuario")
	private Long id;
	
	@Column(name = "nome", length=100, nullable = false)
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@Column(name = "CPF", length=11, unique=true)
	@Attribute(Required = false, SearchField = true)
	private String CPF;
	
	@Column(name = "email", length=150)
	@Attribute(Required = false, SearchField = true)
	private String email;
	
	@Column(name = "telefone1", length=20)
	@Attribute(Required = false, SearchField = false)
	private String telefone1;
	
	@Column(name = "telefone2", length=20)
	@Attribute(Required = false, SearchField = false)
	private String telefone2;
	
	@Column(name = "login", length=20, unique = true, nullable = false)
	@Attribute(Required = true, SearchField = false)
	private String login;
	
	@Column(name = "senha", length=20, nullable = false)
	@Attribute(Required = true, SearchField = false)
	private String senha;
	
	@Column(name = "status" ,nullable = false, columnDefinition="tinyint default 1")
	@Attribute(Required = true, SearchField = false)
	private Boolean status;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_categoria", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = true, SearchField = false)
	private CategoriaUsuario categoria; 
	
	@ManyToMany(cascade ={CascadeType.PERSIST})
	@JoinTable(name="usuario_grupo", 
			joinColumns			={@JoinColumn(name = "id_usua_usgr")}, 
			inverseJoinColumns	={@JoinColumn(name = "id_grus_usgr")}
			)
	private Set<GrupoUsuario> grupos = new HashSet<GrupoUsuario>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario",cascade = CascadeType.ALL, orphanRemoval=true)// mappedBy indica o atributo da entidade many
	@Attribute(Required = false, SearchField = false)
	private Set<UsuarioPermissao> permissoes = new HashSet<UsuarioPermissao>(0);
	
	
	
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

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public CategoriaUsuario getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaUsuario categoria) {
		this.categoria = categoria;
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
