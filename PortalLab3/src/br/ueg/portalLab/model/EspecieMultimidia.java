package br.ueg.portalLab.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.util.media.Media;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

@javax.persistence.Entity

@SuppressWarnings("serial")
@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("  ")
@Table(name="especie_multimidia")
public abstract class EspecieMultimidia<TYPE extends Media> extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_mult")
	protected Long id;
		
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_itta_mult", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = true, SearchField = false)
	protected ItemTaxonomico itemTaxonomico;
	
	/**
	 * O caminho incui o caminho completo, isso é 
	 * inclui o nome do arquivo de midia
	 */
	@Column(name="caminho_mult", length=1000, nullable=false)
	@Attribute(Required = true, SearchField=false)
	protected String caminho;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_esta_mult", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	protected Estacao estacao;
		
	@Transient
	protected TYPE media ;
	
	@Override
	public String toString(){
		String imagem="";
		if(this.getCaminho()!=null)
			imagem+=this.getCaminho();
		
		if(this.getItemTaxonomico()!=null){
			imagem+="( ".concat(this.getItemTaxonomico().getNome().concat(")"));
		}
		if(this.getEstacao()!=null){
			imagem+=" - ".concat(this.getEstacao().getDescricao());
		}
		return imagem;
	}
	
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	

	public TYPE getMedia() {
		if(this.media!=null){
			return media;
		}else{
			media = this.getFileFromCaminho();
			this.setCaminho(media.getName());
			return media;
		}
	}

	public void setMedia(TYPE media) {
		this.setCaminho(media.getName());
		this.media = media;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public ItemTaxonomico getItemTaxonomico() {
		return itemTaxonomico;
	}

	public void setItemTaxonomico(ItemTaxonomico itemTaxonomico) {
		this.itemTaxonomico = itemTaxonomico;
	}

	public Estacao getEstacao() {
		return estacao;
	}

	public void setEstacao(Estacao estacao) {
		this.estacao = estacao;
	}
	
	/**
	 * Esse método deve ser implementado para retornar um
	 *  objeto que implemente a interface Media
	 *  será utilizado para pegar o caminho e carregar
	 *  o objeto com o seu conteudo.
	 */
	public abstract TYPE getFileFromCaminho();
}
