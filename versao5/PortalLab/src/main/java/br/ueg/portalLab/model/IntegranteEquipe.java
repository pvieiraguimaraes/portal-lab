package br.ueg.portalLab.model;

import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.ueg.builderSoft.config.ConfigPortalLab;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.portalLab.utils.ImageUtil;

@javax.persistence.Entity
@SuppressWarnings("serial")
@Table(name="integrante_equipe")
public class IntegranteEquipe<Image> extends EntityImage {

	@Id()
	@GeneratedValue
	@Column(name = "id_integrante")
	private Long id;
	
	@Column(name = "nome", nullable=false)
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@Column(name = "descricao", length=275, nullable=false)
	@Attribute(Required = true, SearchField = true)
	private String descricao;
	
	@Column(name = "link_lates")
	@Attribute(Required = false, SearchField = true)
	private String linkLates;
		
	
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
	
	
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the linkLates
	 */
	public String getLinkLates() {
		return linkLates;
	}

	/**
	 * @param linkLates the linkLates to set
	 */
	public void setLinkLates(String linkLates) {
		this.linkLates = linkLates;
	}

	@Override
	public String getTypeMediaSimpleName() {
		return "integrante_equipe";
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.portalLab.model.EntityMedia#getDefaultMediaName()
	 */
	@Override
	public String getDefaultMediaName() {
		return  "integrante_equipe.jpg";
	}
	@Override
	public InputStream getThumbImageInputStream(InputStream streamMedia) throws Exception {
		
		int width = ConfigPortalLab.getInstancia().getIntegranteImageWidth();
		int height = ConfigPortalLab.getInstancia().getIntegranteImageHeight();
		
		return ImageUtil.scaleImage(streamMedia, width, height);
	}


}
