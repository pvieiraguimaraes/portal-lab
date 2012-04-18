package br.ueg.builderSoft.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;



import br.ueg.builderSoft.util.annotation.Attribute;

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
	@Column(name = "id_item")
	private Long id;
	@Column(name = "descricao")
	@Attribute(Required = true, SearchField = true)
	private String descricao;
	
	
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao(){
		return this.descricao;
	}
	
	public void setDescricao(String vDescricao){
		this.descricao = vDescricao;
	}

}
