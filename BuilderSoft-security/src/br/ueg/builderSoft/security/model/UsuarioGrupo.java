package br.ueg.builderSoft.security.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;



@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "usuario_grupo")
public class UsuarioGrupo extends Entity  {
	
	@Id
	@Column(name = "codigo_usgr")
	private long id;	

	@Column(name = "codigo_grup_usgr")
	@Attribute(Required = true, SearchField = false)
	private long idGrupo;
	
	@Column(name = "codigo_usua_usgr")
	@Attribute(Required = true, SearchField = false)
	private long idUsuario;
	
	public UsuarioGrupo(){		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the idGrupo
	 */
	public long getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo the idGrupo to set
	 */
	public void setIdGrupo(long idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * @return the idUsuario
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
}
