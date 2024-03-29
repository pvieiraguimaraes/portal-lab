package br.ueg.portalLab.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

@javax.persistence.Entity
@SuppressWarnings("serial")
@Table(name="laboratorio")
public class Laboratorio extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_laboratorio")
	private Long id;
	
	@Column(name = "nome", length=50, unique=true)
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@Column(name = "sigla", length=8, unique=true)
	@Attribute(Required = true, SearchField = true)
	private String sigla;
	
	@Column(name = "responsavel", length=100)
	@Attribute(Required = false, SearchField = false)
	private String responsavel;
	
	@Column(name = "telefone", length=20)
	@Attribute(Required = false, SearchField = true)
	private String telefone;
	
	
	
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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString(){
		if(this.getNome()!=null && this.getSigla()!=null){
			return this.getNome().concat(" ( ").concat(this.getSigla()).concat(" )");
		}else{
			return "Nao informado";
		}
	}
	
	
}
