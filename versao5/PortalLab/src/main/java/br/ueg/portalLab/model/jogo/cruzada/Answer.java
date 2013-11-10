package br.ueg.portalLab.model.jogo.cruzada;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Hibernate;

import br.ueg.builderSoft.model.Entity;

@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name="cruzadinha_resposta")
public class Answer extends Entity{

	@Id()
	@GeneratedValue
	@Column(name = "id_resp")
	private Long id;
	@Enumerated
	@Column(name="tipo")
	TypeAnswer type;
	@Column(name="posicao_inicial_x")
	Integer startPositionX;
	@Column(name="posicao_inicial_y")
	Integer startPositionY;
	@Column(name="valor")
	String Value;
	@Column(name="dica")
	String hint;
	@Column(name="fixa")
	Boolean fixe = false;
	@Transient
	Boolean answeredCorrectly = false;
	@Transient
	List<Square> squares = new ArrayList<Square>();
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=CrossWord.class,cascade={CascadeType.PERSIST})
	@JoinColumn(name="id_cruzadinha")
	CrossWord crossword;
	
	
	public TypeAnswer getType() {
		return type;
	}
	public void setType(TypeAnswer type) {
		this.type = type;
	}
	public Integer getStartPositionX() {
		return startPositionX;
	}
	public void setStartPositionX(Integer startPositionX) {
		this.startPositionX = startPositionX;
	}
	public Integer getStartPositionY() {
		return startPositionY;
	}
	public void setStartPositionY(Integer startPositionY) {
		this.startPositionY = startPositionY;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public Boolean getFixe() {
		return fixe;
	}
	public void setFixe(Boolean fixe) {
		this.fixe = fixe;
	}
	public String getFormatedAnswerPosition(){
		return this.getStartPositionX() + "." + this.getStartPositionY();
	}
	public Boolean getAnsweredCorrectly() {
		return answeredCorrectly;
	}
	public void setAnsweredCorrectly(Boolean answeredCorrectly) {
		this.answeredCorrectly = answeredCorrectly;
	}
	public List<Square> getSquares() {
		return squares;
	}
	public void setSquares(List<Square> squares) {
		this.squares = squares;
	}
	public CrossWord getCrossword() {
		return crossword;
	}
	public void setCrossword(CrossWord crossword) {
		this.crossword = crossword;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (Hibernate.getClass(this) != Hibernate.getClass(obj))
			return false;
		Entity other = (Entity) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
			///VALIDAÇÃO PARA FUNCIONAR MODO ADMINISTRADOR DA CRUZADINHA
			if (other instanceof Answer)
			{
				if(this.getStartPositionX() != ((Answer) other).getStartPositionX() ||
				   this.getStartPositionY() != ((Answer) other).getStartPositionY() 	
				)
					return false;
			}
			///VALIDÇÃO PARA FUNCIONAR MODO ADMINISTRADOR DA CRUZADINHA END
			
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}
