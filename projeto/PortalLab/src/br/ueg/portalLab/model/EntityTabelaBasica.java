package br.ueg.portalLab.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;



import br.ueg.portalLab.util.annotation.Attribute;

@javax.persistence.Entity
@SuppressWarnings("serial")
//@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tabela", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("  ")
@Table(name="tabela_basica")
public abstract class EntityTabelaBasica extends Entity {

		@Id()
	@GeneratedValue
	//@OneToMany(targetEntity = Filo.class, mappedBy = "reino")
	@Column(name = "id_item")
	private long id;
	@Column(name = "descricao")
	@Attribute(Required = true, SearchField = true)
	private String descricao;
	
/*	@Column(name="tabela")
	@Attribute(Required = true, SearchField = true)
	private String tabela;*/
	
	
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}
	
/*	public String getTabela(){
		return this.tabela;
	}
	public void setTabela(String tabela){
		this.tabela = tabela;
	}
	*/
	public String getDescricao(){
		return this.descricao;
	}
	
	public void setDescricao(String vDescricao){
		this.descricao = vDescricao;
	}

}
