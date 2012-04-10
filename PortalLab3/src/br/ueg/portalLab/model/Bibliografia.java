package br.ueg.portalLab.model;

import java.text.DateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.builderSoft.util.control.MessagesControl;

@javax.persistence.Entity
@SuppressWarnings("serial")
@Table(name="bibliografia")
public class Bibliografia extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_bibliografia")
	private long id;
	
	@Column(name = "data")
	@Attribute(Required = false, SearchField = false)
	private Date data;
	
	@Column(name = "titulo_da_obra", length=250)
	@Attribute(Required = true, SearchField = true)
	private String tituloDaObra;
	
	@Column(name = "titulo_da_publicacao", length=250)
	@Attribute(Required = false, SearchField = true)
	private String tituloDaPublicacao;
	
	@Column(name = "editora", length=100)
	@Attribute(Required = false, SearchField = true)
	private String editora;
	
	@Column(name = "valume", length=50)
	@Attribute(Required = false, SearchField = true)
	private String volume;
	
	@Column(name = "paginas", length=20)
	@Attribute(Required = false, SearchField = true)
	private String paginas;
		
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_autor", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = true, SearchField = false)
	private Autor autor; 
	
	@Override
	public String toString(){
		String bibliografia="";
		if(autor!=null)
			bibliografia+=autor.getDescricao();
		
		if(data!=null){
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			bibliografia+=", ".concat(df.format(data));
		}else {
			bibliografia+=", ";
		}
		
		if(tituloDaObra!=null){
			bibliografia+=", ".concat(tituloDaObra);
		}else{
			bibliografia+=", ";
		}
		
		if(tituloDaPublicacao!=null){
			bibliografia+=", ".concat(tituloDaPublicacao);
		}else{
			bibliografia+=", ";
		}
		
		if(editora!=null){
			bibliografia+=", ".concat(editora);
		}else{
			bibliografia+=", ";
		}
		
		if(volume!=null){
			bibliografia+=", ".concat(volume);
		}else{
			bibliografia+=", ";
		}
		
		if(paginas!=null){
			bibliografia+=", ".concat(paginas);
		}else{
			bibliografia+=", ";
		}
		
		return bibliografia;
	}
	
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTituloDaObra() {
		return tituloDaObra;
	}

	public void setTituloDaObra(String tituloDaObra) {
		this.tituloDaObra = tituloDaObra;
	}

	public String getTituloDaPublicacao() {
		return tituloDaPublicacao;
	}

	public void setTituloDaPublicacao(String tituloDaPublicacao) {
		this.tituloDaPublicacao = tituloDaPublicacao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getPaginas() {
		return paginas;
	}

	public void setPaginas(String paginas) {
		this.paginas = paginas;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	

	
	
}
