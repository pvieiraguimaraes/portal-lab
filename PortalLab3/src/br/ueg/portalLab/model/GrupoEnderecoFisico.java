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
@Table(name="grupo_endereco")
public class GrupoEnderecoFisico extends Entity {



	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.model.Entity#getColumnCompare()
	 */
	@Override
	protected String getColumnCompare() {
		return this.getNome();
	}

	@Id()
	@GeneratedValue
	@Column(name = "id_grupo")
	private Long id;
	
	@Column(name = "nome", length=100)
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
		
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_laboratorio", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = true, SearchField = false)
	private Laboratorio laboratorio; 
	
	
	
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

	@Override
	public String toString(){
		return this.getNome();
	}
	
}
