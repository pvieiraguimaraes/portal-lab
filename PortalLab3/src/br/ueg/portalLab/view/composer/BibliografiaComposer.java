package br.ueg.portalLab.view.composer;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.zkoss.zkplus.databind.BindingListModelList;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.model.Autor;
import br.ueg.portalLab.model.Bibliografia;

@org.springframework.stereotype.Component
@Scope("desktop")
public class BibliografiaComposer extends TabelaComposerController<Bibliografia> {
	

	private static final long serialVersionUID = -3381825033594154312L;

	@AttributeView(key = "data", isEntityValue = true, fieldType = Date.class, isVisible=true, caption="bibliografia_dataColumn")
	private Date fldData=new Date();
	
	@AttributeView(key = "tituloDaObra", isEntityValue = true, fieldType = String.class, isVisible=true, caption="bibliografia_tituloDaObraColumn")
	private String fldTituloDaObra;
	
	@AttributeView(key = "tituloDaPublicacao", isEntityValue = true, fieldType = String.class, isVisible=true, caption="bibliografia_tituloDaPublicacaoColumn")
	private String fldTituloDaPublicacao;
	
	@AttributeView(key = "editora", isEntityValue = true, fieldType = String.class, isVisible=true, caption="bibliografia_editoraColumn")
	private String fldEditora;
	
	@AttributeView(key = "volume", isEntityValue = true, fieldType = String.class, isVisible=true, caption="bibliografia_volumeColumn")
	private String fldVolume;
	
	@AttributeView(key = "paginas", isEntityValue = true, fieldType = String.class, isVisible=true, caption="bibliografia_paginasColumn")
	private String fldPaginas;
		
	@AttributeView(key = "autor", isEntityValue = true, fieldType = Autor.class, isVisible=true, caption="bibliografia_autorColumn")
	private Autor fldAutor; 
	
	

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return Bibliografia.class; 
	}

	
	
	public Date getFldData() {
		return fldData;
	}



	public void setFldData(Date fldData) {
		this.fldData = fldData;
	}



	public String getFldTituloDaObra() {
		return fldTituloDaObra;
	}



	public void setFldTituloDaObra(String fldTituloDaObra) {
		this.fldTituloDaObra = fldTituloDaObra;
	}



	public String getFldTituloDaPublicacao() {
		return fldTituloDaPublicacao;
	}



	public void setFldTituloDaPublicacao(String fldTituloDaPublicacao) {
		this.fldTituloDaPublicacao = fldTituloDaPublicacao;
	}



	public String getFldEditora() {
		return fldEditora;
	}



	public void setFldEditora(String fldEditora) {
		this.fldEditora = fldEditora;
	}



	public String getFldVolume() {
		return fldVolume;
	}



	public void setFldVolume(String fldVolume) {
		this.fldVolume = fldVolume;
	}



	public String getFldPaginas() {
		return fldPaginas;
	}



	public void setFldPaginas(String fldPaginas) {
		this.fldPaginas = fldPaginas;
	}



	public Autor getFldAutor() {
		return fldAutor;
	}



	public void setFldAutor(Autor fldAutor) {
		this.fldAutor = fldAutor;
	}



	public BindingListModelList<Entity> getListAutor(){
		return this.getFKEntityModel("fldAutor");
	}
}
