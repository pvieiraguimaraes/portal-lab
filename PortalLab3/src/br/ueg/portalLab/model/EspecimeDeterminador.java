package br.ueg.portalLab.model;

import java.text.DateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.builderSoft.util.control.MessagesControl;

@javax.persistence.Entity
@SuppressWarnings("serial")
@Table(name="especime_determinador")
public class EspecimeDeterminador extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_epde")
	private Long id;
	
	@Column(name = "data")
	@Attribute(Required = false, SearchField = false)
	private Date data;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_espe_epde", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = true, SearchField = false)
	private Especime especime;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dete_epde", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = true, SearchField = false)
	private Determinador determinador; 
	
	@Override
	public String toString(){
		String deter="";
		if(determinador!=null)
			deter+=determinador.getDescricao();
		
		if(data!=null){
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			deter+="( ".concat(df.format(data)).concat(")");
		}
		return deter;
	}
	
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Especime getEspecime() {
		return especime;
	}

	public void setEspecime(Especime especime) {
		this.especime = especime;
	}

	public Determinador getDeterminador() {
		return determinador;
	}

	public void setDeterminador(Determinador determinador) {
		this.determinador = determinador;
	}

}
