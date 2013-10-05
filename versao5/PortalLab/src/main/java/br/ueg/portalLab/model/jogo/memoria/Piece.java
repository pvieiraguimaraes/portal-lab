package br.ueg.portalLab.model.jogo.memoria;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;

@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name="memoria_peca")
public class Piece extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_piece")
	private Long id;
	
	@Column(name = "path_image")
	private String pathImage;
	
	@Column(name = "path_image_succeed")
	private String pathImageSucceed;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="memory_id")
	private Memory memory;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

	public String getPathImageSucceed() {
		return pathImageSucceed;
	}

	public void setPathImageSucceed(String pathImageSucceed) {
		this.pathImageSucceed = pathImageSucceed;
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}
	
}
