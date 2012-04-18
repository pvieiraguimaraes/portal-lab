package br.ueg.portalLab.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

@javax.persistence.Entity
@SuppressWarnings("serial")
@Table(name="categoria_usuario")
public class CategoriaUsuario extends Entity {

	@Id()
	@GeneratedValue
	//@OneToMany(targetEntity = Filo.class, mappedBy = "reino")
	@Column(name = "id_categoria")
	private Long id;
	
	@Column(name = "nome")
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@Column(name = "cpf_obrigatorio")
	@Attribute(Required = true, SearchField = false)
	private boolean isCPFObrigatorio;
	
	@Column(name = "observacoes")
	@Attribute(Required = false, SearchField = true)
	private String observacoes;
	
	
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

	public boolean isCPFObrigatorio() {
		return isCPFObrigatorio;
	}
	
	//Metodo para compatibilizar com o funcionamento do mapto
	//TODO mudar o parsettoEntity
	public boolean getIsCPFObrigatorio(){
		return this.isCPFObrigatorio();
	}

	public void setCPFObrigatorio(boolean isCPFObrigatorio) {
		this.isCPFObrigatorio = isCPFObrigatorio;
	}
/*	//metodo para compatiblizar como funcionamento do nosso framework
	//TODO modificar para trabalhar corretamente.
	public void setIsCPFObrigatorio(boolean isCPFObrigatorio){
		this.setCPFObrigatorio(isCPFObrigatorio);
	}*/
	//metodo utilizado para 
	public void setIsCPFObrigatorio(Boolean isCPFObrigatorio){
		this.setCPFObrigatorio(isCPFObrigatorio.booleanValue());
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@Override
	public String getColumnCompare(){
		return this.getNome();
	}
	
}
