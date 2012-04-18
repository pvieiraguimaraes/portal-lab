package br.ueg.portalLab.security.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;



/**
 * @author guiliano
 * são os Papeis(roles) do springSecurity
 */
@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "casodeuso_funcionalidade",uniqueConstraints ={@UniqueConstraint(columnNames={"id_caso_cafu","id_func_cafu"})})
public class CasoDeUsoFuncionalidade extends Entity  {
	
	@Id
	@GeneratedValue	
	@Column(name = "id_cafu")
	private Long id;	

	@ManyToOne(optional = false , fetch = FetchType.LAZY)
	@JoinColumn(name = "id_caso_cafu", insertable = true, updatable = true, nullable=false)
	@Attribute(Required = false, SearchField = false)
	private CasoDeUso casoDeUso;
	
	@ManyToOne(optional = false , fetch = FetchType.LAZY)
	@JoinColumn(name = "id_func_cafu", insertable = true, updatable = true, nullable=false)
	@Attribute(Required = false, SearchField = true)
	private Funcionalidade funcionalidade;
	
	@Transient
	private boolean controleInsercaoPadrao = true;
	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.model.Entity#isNew()
	 */
	@Override
	public boolean isNew() {
		if(this.isControleInsercaoPadroa()){
			return super.isNew();
		}else{
			return false;
		}
	}

	public CasoDeUsoFuncionalidade(){		
	}
			
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	/**
	 * @return the controleInsercaoPadroa
	 */
	public boolean isControleInsercaoPadroa() {
		return controleInsercaoPadrao;
	}
	public boolean getControleInsercaoPadrao(){
		return this.isControleInsercaoPadroa();
	}

	/**
	 * @param controleInsercaoPadroa the controleInsercaoPadroa to set
	 */
	public void setControleInsercaoPadroa(boolean controleInsercaoPadroa) {
		this.controleInsercaoPadrao = controleInsercaoPadroa;
	}

	@Override
	public String toString(){
		String retorno = "";
		if(this.getCasoDeUso()!=null){
			retorno = this.getCasoDeUso().getNome();
		}
		if(this.getFuncionalidade()!=null){
			retorno += " : " + this.getFuncionalidade().getNome();
		}
		return retorno;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	
}
