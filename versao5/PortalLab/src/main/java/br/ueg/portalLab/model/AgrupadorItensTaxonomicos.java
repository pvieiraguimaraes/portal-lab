package br.ueg.portalLab.model;

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
@Table(name="agrupador_itemtaxonomico")
public class AgrupadorItensTaxonomicos extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_agrupadoresitens")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_agrupador", nullable=false)
	private Agrupador agrupador;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_itemtaxonomico", nullable=false)
	private ItemTaxonomico item;
	
	public Agrupador getAgrupador() {
		return agrupador;
	}

	public void setAgrupador(Agrupador agrupador) {
		this.agrupador = agrupador;
	}

	public ItemTaxonomico getItem() {
		return item;
	}

	public void setItem(ItemTaxonomico item) {
		this.item = item;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
