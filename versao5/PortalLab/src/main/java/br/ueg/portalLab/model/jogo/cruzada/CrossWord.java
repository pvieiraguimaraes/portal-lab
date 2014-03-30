package br.ueg.portalLab.model.jogo.cruzada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name="cruzadinha")
public class CrossWord extends Entity{

	@Id()
	@GeneratedValue
	@Column(name = "id_cruz")
	private Long id;
	@Column(name = "nome")
	@Attribute(Required = true, SearchField = true)
	private String name;
	@Column(name = "dimensao_x_colunas")
	private Integer crossXDimension; /// Colunas
	@Column(name = "dimensao_y_linhas")
	private Integer crossYDimension; /// Linhas
	@OneToMany(fetch=FetchType.EAGER, mappedBy="crossword", targetEntity=Answer.class,orphanRemoval=true ,cascade={CascadeType.PERSIST,CascadeType.ALL,CascadeType.REMOVE})
	@Cascade(value={org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE})
	private List<Answer> answers = new ArrayList<Answer>();
	@Transient
	private HashMap<String,Square> squares = new HashMap<String,Square>();
	
	public Integer getCrossXDimension() {
		return crossXDimension;
	}
	public void setCrossXDimension(Integer crossXDimension) {
		this.crossXDimension = crossXDimension;
	}
	public Integer getCrossYDimension() {
		return crossYDimension;
	}
	public void setCrossYDimension(Integer crossYDimension) {
		this.crossYDimension = crossYDimension;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	public HashMap<String,Square> getSquares() {
		return squares;
	}
	public void setSquares(HashMap<String,Square> squares) {
		this.squares = squares;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	



}
