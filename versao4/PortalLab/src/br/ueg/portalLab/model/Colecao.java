package br.ueg.portalLab.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

@javax.persistence.Entity
@SuppressWarnings("serial")
@Table(name="colecao")
public class Colecao extends Entity {



	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.model.Entity#getColumnCompare()
	 */
	@Override
	protected String getColumnCompare() {
		return this.getNome();
	}

	@Id()
	@GeneratedValue
	@Column(name = "id_colecao")
	private Long id;
	
	@Column(name = "nome_cole", length=100)
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
		
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_laboratorio", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = true, SearchField = false)
	private Laboratorio laboratorio; 
	
	@Column(name="sigla", length=10)
	@Attribute(Required = true, SearchField = true)
	private String sigla;
	
	
	
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

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Override
	public String toString(){
		String sigla = this.getSigla();
		if(sigla !=null){
			return this.getNome().concat("(").concat(this.getSigla()).concat(")");
		}else{
			return this.getNome();
		}
	}
	
}
