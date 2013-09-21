package br.ueg.portalLab.model;

import java.io.FileInputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

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

import org.hibernate.LazyInitializationException;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;

import br.ueg.builderSoft.config.ConfigPortalLab;
import br.ueg.builderSoft.util.annotation.Attribute;

//@javax.persistence.Entity

@SuppressWarnings("serial")
@MappedSuperclass
@Inheritance(strategy=InheritanceType.JOINED)
//@DiscriminatorColumn(name="tipo", discriminatorType=DiscriminatorType.STRING)
//@DiscriminatorValue("  ")
//@Table(name="especie_multimidia")
public abstract class EspecieMultimidia<TYPE extends Media> extends EntityMedia<TYPE> {

	@Id()
	@GeneratedValue
	@Column(name = "id_mult")
	protected Long id;
		
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_itta_mult", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = true, SearchField = true)
	protected ItemTaxonomico itemTaxonomico;
	
	
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_esta_mult", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = true)
	protected Estacao estacao;
		
	@Override
	public String toString(){
		String imagem="";
		if(this.getFileName()!=null)
			imagem+=this.getFileName();
		try {
			if (this.getItemTaxonomico() != null) {
				imagem += "( ".concat(this.getItemTaxonomico().getNome()
						.concat(")"));
			}
			if (this.getEstacao() != null) {
				imagem += " - ".concat(this.getEstacao().getDescricao());
			}
		} catch (Exception e) {
			
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
	
	/** retorno o path dentro do contexto da aplicação, só que utilizando o 
	 * caracere de separação de diretório do sistema operacional 
	 * e não o separador de url
	 * @param endWithSeparator
	 * @param url indica se é para retornar camiha de url ou de sistema operacional, true-> url, false-> sistema
	 * @return String Caminho do path de imagem
	 */
	public String getContextPathMedia(boolean endWithSeparator, boolean url) {
		String separator = System.getProperty("file.separator");
		String diretorioImagem = ConfigPortalLab.getInstancia().getMediaWebPath();
		ItemTaxonomico itemTaxonomico2 = this.getItemTaxonomico();
		String nomeCompleto  = "";
		if(itemTaxonomico2!=null){ 
			nomeCompleto = itemTaxonomico2.getNomeCompleto();			
		}
		String auxPath = url?separator.concat(diretorioImagem):"";
		
		String contextPathMedia = auxPath
				.concat(separator)
				.concat(nomeCompleto)
				.concat(separator)
				.concat(this.getTypeMediaSimpleName())
				.concat(separator)
				.concat(this.getEstacao() != null ? this.getEstacao()
						.getDescricao() : "")
				.concat(endWithSeparator ? separator : "");
		if(url){
			contextPathMedia = contextPathMedia.replace(separator, "/");
		}
		return contextPathMedia;
	}
	
}
