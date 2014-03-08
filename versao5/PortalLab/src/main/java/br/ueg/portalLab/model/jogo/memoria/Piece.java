package br.ueg.portalLab.model.jogo.memoria;

import java.io.File;
import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.model.interfaces.AttributesMedia;
import br.ueg.builderSoft.util.annotation.Image;
import br.ueg.builderSoft.util.annotation.Media;

@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name="memoria_peca")
public class Piece extends Entity implements AttributesMedia {

	@Id()
	@GeneratedValue
	@Column(name = "id_piece")
	private Long id;
	
	
	@Transient
	@Media("image")
	@Image(height="piece_height", width="piece_width", thumb=true, thumbHeight="50", thumbWidth="50")
	private InputStream image;
	
	@Transient
	@Media("image_succeed")
	@Image(height="piece_height", width="piece_width", thumb=true, thumbHeight="50", thumbWidth="50")
	private InputStream imageSucceed;
	
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

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public InputStream getImageSucceed() {
		return imageSucceed;
	}

	public void setImageSucceed(InputStream imageSucceed) {
		this.imageSucceed = imageSucceed;
	}

	@Override
	public String getBasePath() {
		return "C:" + File.separator + "portallab" + File.separator;
	}

	@Override
	public String getRelativePath() {
		return "jogos" + File.separator + "memoria" + File.separator + getId() + File.separator;
	}
	
}
