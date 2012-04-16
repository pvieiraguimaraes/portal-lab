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



/**
 * @author guiliano
 * são os Papeis(roles) do springSecurity
 */
@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "casodeuso_funcionalidade")
public class CasoDeUsoFuncionalidade extends Entity  {
	
	@Id
	@GeneratedValue	
	@Column(name = "id_cafu")
	private long id;	

	@ManyToOne(optional = true , fetch = FetchType.LAZY)
	@JoinColumn(name = "id_caso_cafu", insertable = true, updatable = true, nullable=false)
	@Attribute(Required = false, SearchField = false)
	private CasoDeUso casoDeUso;
	
	@ManyToOne(optional = true , fetch = FetchType.LAZY)
	@JoinColumn(name = "id_func_cafu", insertable = true, updatable = true, nullable=false)
	@Attribute(Required = false, SearchField = true)
	private Funcionalidade funcionalidade;
	
	
	public CasoDeUsoFuncionalidade(){		
	}
			
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the casoDeUso
	 */
	public CasoDeUso getCasoDeUso() {
		return casoDeUso;
	}

	/**
	 * @param casoDeUso the casoDeUso to set
	 */
	public void setCasoDeUso(CasoDeUso casoDeUso) {
		this.casoDeUso = casoDeUso;
	}

	/**
	 * @return the funcionalidade
	 */
	public Funcionalidade getFuncionalidade() {
		return funcionalidade;
	}

	/**
	 * @param funcionalidade the funcionalidade to set
	 */
	public void setFuncionalidade(Funcionalidade funcionalidade) {
		this.funcionalidade = funcionalidade;
	}

	
}
