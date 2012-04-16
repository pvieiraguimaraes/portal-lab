package br.ueg.builderSoft.security.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;



@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "usuario_permissao")
public class UsuarioPermissao extends Entity  {
	
	@Id
	@GeneratedValue	
	@Column(name = "codigo_uspe")
	private long id;			
	
	@Column(name = "permitido_uspe", nullable=false)
	@Attribute(Required = true, SearchField = false)
	private Boolean permitido = true;
	
	@ManyToOne(optional = true , fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usua_uspe", insertable = true, updatable = true, nullable=false)
	@Attribute(Required = false, SearchField = false)
	private Usuario usuario;
	
	@ManyToOne(optional = true , fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cafu_uspe", insertable = true, updatable = true, nullable=false)
	@Attribute(Required = false, SearchField = false)
	private CasoDeUsoFuncionalidade casoDeUsoFuncionalidade;
	
	
	
	public UsuarioPermissao(){		
	}
			
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the permitido
	 */
	public Boolean getPermitido() {
		return permitido;
	}

	/**
	 * @param permitido the permitido to set
	 */
	public void setPermitido(Boolean permitido) {
		this.permitido = permitido;
	}

	/**
	 * @return the casoDeUsoFuncionalidade
	 */
	public CasoDeUsoFuncionalidade getCasoDeUsoFuncionalidade() {
		return casoDeUsoFuncionalidade;
	}

	/**
	 * @param casoDeUsoFuncionalidade the casoDeUsoFuncionalidade to set
	 */
	public void setCasoDeUsoFuncionalidade(CasoDeUsoFuncionalidade casoDeUsoFuncionalidade) {
		this.casoDeUsoFuncionalidade = casoDeUsoFuncionalidade;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
