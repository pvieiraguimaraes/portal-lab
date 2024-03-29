package br.ueg.portalLab.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

/**
 * Classe Teste que cont�m relacionamento com Reino.
 * @author Diego
 *
 */
@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name = "item_taxonomico")
public class ItemTaxonomico extends Entity {

	@Id
	@GeneratedValue
	@Column(name = "id_ittax")
	private Long id;
	
	@Column(name = "nome_ittax")
	@Attribute(Required = true, SearchField = true)
	@OrderBy("nome")
	private String nome;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nigeo_ittax", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = true, SearchField = true)
	private NivelTaxonomico nivelTaxonomico;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pai_ittax", insertable = true, updatable = true, nullable=true)
	private ItemTaxonomico pai; 
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pai",cascade = CascadeType.ALL)// mappedBy indica o atributo da entidade many	
	private Set<ItemTaxonomico> filhosItensTaxonomicos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "itemTaxonomico",cascade = CascadeType.ALL)// mappedBy indica o atributo da entidade many	
	private Set<EspecieImagem> imagens;
	
	@Attribute(Required=false, SearchField = true)
	@Column(name="caracteristica_ittax", length=4000)
	private String caracteristica;
	
	@Attribute(Required=true, SearchField = true)
	@Column(name="nome_popular_ittax", length=100)
	private String nomePopular;
	
	public ItemTaxonomico() {}
	

	public ItemTaxonomico(long id, String nome,
			NivelTaxonomico nivelTaxonomico, ItemTaxonomico pai) {
		this.id = id;
		this.nome = nome;
		this.nivelTaxonomico = nivelTaxonomico;
		this.pai = pai;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public NivelTaxonomico getNivelTaxonomico() {
		return nivelTaxonomico;
	}


	public void setNivelTaxonomico(NivelTaxonomico nivelTaxonomico) {
		this.nivelTaxonomico = nivelTaxonomico;
	}


	public ItemTaxonomico getPai() {
		return pai;
	}


	public void setPai(ItemTaxonomico pai) {
		this.pai = pai;
	}


	public Set<ItemTaxonomico> getFilhosItensTaxonomicos() {
		return filhosItensTaxonomicos;
	}


	public void setFilhosItensTaxonomicos(Set<ItemTaxonomico> filhosItensTaxonomicos) {
		this.filhosItensTaxonomicos = filhosItensTaxonomicos;
	}
	
	@Override
	public String toString(){
		String retorno = "";
		//System.err.println(this.getNome());
		/*try{
			retorno = this.getNivelTaxonomico().getNome();
		}catch(Exception e){
			//e.printStackTrace();
		}*/
		//return retorno+" "+this.getNome();
		return this.getNome();
	}
	
	public String getNomePopularCompleto(){
		String nomePopular2 = this.getNomePopular();
		nomePopular2 = nomePopular2!=null?nomePopular2:"";
		return nomePopular2+"( "+this.getNome()+" )";
	}


	public Set<EspecieImagem> getImagens() {
		return imagens;
	}


	public void setImagens(Set<EspecieImagem> imagens) {
		this.imagens = imagens;
	}
	
	/**
	 * @return the caracteristica
	 */
	public String getCaracteristica() {
		return caracteristica;
	}


	/**
	 * @param caracteristica the caracteristica to set
	 */
	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}


	public String getNomePopular() {
		return nomePopular;
	}


	public void setNomePopular(String nomePopular) {
		this.nomePopular = nomePopular;
	}


	@SuppressWarnings({ "rawtypes" })
	public  Set<EspecieMultimidia> getSetMediaByTypeName(String typeName){
		Set<EspecieMultimidia> setMedia = new HashSet<EspecieMultimidia>(0);
		
		if(typeName.equalsIgnoreCase("image")){
				//TODO mudan�a superEspecieImagem
				setMedia.addAll((Collection<? extends EspecieMultimidia>) this.getImagens());
			
			return setMedia;
		}
		return setMedia;
	}
	
	public String getNomeCompleto(){
		String retorno = null;
		String separator = System.getProperty("file.separator");
		if(this.getPai()!=null){
			retorno = this.getPai().getNomeCompleto() + separator + this.getNome();
		}else{
			retorno = this.getNome();	
		}
		
		return retorno;
	}

}