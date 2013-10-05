package br.ueg.portalLab.model.jogo.memoria;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name="memoria")
public class Memory extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_memory")
	private Long id;
	
	@Column(name = "name")
	@Attribute(Required = true, SearchField = true)
	private String name;

	@Column(name = "number_pieces")
	private Integer numberPieces;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="memory", targetEntity = Piece.class)
	private List<Piece> pieces;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumberPieces() {
		return numberPieces;
	}

	public void setNumberPieces(Integer numberPieces) {
		this.numberPieces = numberPieces;
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}
	
}
