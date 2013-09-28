package br.ueg.portalLab.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.zkoss.util.media.Media;

import br.ueg.builderSoft.config.ConfigPortalLab;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;
@MappedSuperclass
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class EntityMedia<TYPE extends Media> extends Entity {

	@Attribute(Required = true, SearchField = false)
	@Transient
	protected TYPE media;
	@Transient
	private boolean controleInsercaoPadrao = true;
	
	/**
	 * O caminho incui o caminho completo, isso � 
	 * inclui o nome do arquivo de midia
	 */
	@Column(name="caminho", length=1000, nullable=false)
	@Attribute(Required = true, SearchField=true)
	protected String fileName;
	
	/** Retorna o nome do media padr�o
	 * @return
	 */
	public abstract String getDefaultMediaName();
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String caminho) {
		this.fileName = caminho;
	}

	/**
	 * Esse método devolve a extensão do arquivo representado pelo objeto File
	 * passado como parâmetro.
	 * 
	 * @param file
	 * @return
	 */
	public static String getExtension(String pathfile) {
		String ext = pathfile.substring(pathfile.lastIndexOf('.') + 1);
		return ext;
	}

	public TYPE getMedia() {
		if(this.media!=null){
			return media;
		}else{
			media = this.getFileFromCaminho();
			if(media!=null){
				this.setFileName(media.getName());
			}else{
				media = this.getDefaultMedia();
			}
			return media;
		}
	}

	public void setMedia(TYPE media) {
		if(media!=null){
			this.setFileName(media.getName());
		}
		this.media = media;
	}

	/**
	 * Esse método deve ser implementado para retornar um
	 *  objeto que implemente a interface Media
	 *  será utilizado para pegar o caminho e carregar
	 *  o objeto com o seu conteudo.
	 */
	public abstract TYPE getFileFromCaminho();

	/** Metodo utilizado para retornar uma multimidia padrão 
	 * caso não seja selecionadon nenhum outro multimidia.
	 * 
	 * @return TYPE tipo definido na herança
	 */
	public abstract TYPE getDefaultMedia();

	@Override
	public boolean isNew() {
		if (this.isControleInsercaoPadroa()) {
			return super.isNew();
		} else {
			return true;
		}
	}

	/**
	 * @return the controleInsercaoPadroa
	 */
	public boolean isControleInsercaoPadroa() {
		return controleInsercaoPadrao;
	}

	public boolean getControleInsercaoPadrao() {
		return this.isControleInsercaoPadroa();
	}

	/**
	 * @param controleInsercaoPadroa
	 *            the controleInsercaoPadroa to set
	 */
	public void setControleInsercaoPadroa(boolean controleInsercaoPadroa) {
		this.controleInsercaoPadrao = controleInsercaoPadroa;
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
		
		String auxPath = url?separator.concat(diretorioImagem):"";
		
		String contextPathMedia = auxPath
				.concat(separator)
				.concat(this.getTypeMediaSimpleName())
				.concat(separator)
				.concat(endWithSeparator ? separator : "");
		if(url){
			contextPathMedia = contextPathMedia.replace(separator, "/");
		}
		return contextPathMedia;
	}

	/** 
	 * @param endWithSeparator indica se é para adicionar o separador de diretório no final
	 * @return retorna o diretorio onde as imagens devem ser salvas conforme a taxonomia
	 */
	public String getDirectoryMedia(boolean endWithSeparator) {
		String separator = System.getProperty("file.separator");
	
		
		//String diretorioRoot = ConfigPortalLab.getInstancia().getRootApplicationPath();
		String diretorioRoot = ConfigPortalLab.getInstancia().getDireitorioMedia();
		
		String contextPathImage = getContextPathMedia(endWithSeparator,false);
	
		return diretorioRoot
				.concat(separator)
				.concat(contextPathImage);
	}

	/**
	 * @return retorno o diretorio que sera utilizado para separar ot tipo de multimidia dentro da
	 * pasta do item taxonomico associado ao mulitmidia.
	 */
	public abstract String getTypeMediaSimpleName();

	/**
	 * @return String url da imagem direto da aplicação de media
	 */
	public String getMediaURLPath() {
		String auxPath = this.getContextPathMedia(true, true);
		auxPath = auxPath.concat(this.getFileName());
		return auxPath;
	}

	public EntityMedia() {
		super();
	}

	protected int writeInputStreamToDisk(String absoluteFilePath, InputStream media)
			throws FileNotFoundException, IOException {
				int retorno;
			
				if (new File(absoluteFilePath).exists())
					return 2;
			
				OutputStream outputStream = new FileOutputStream(absoluteFilePath);
			
				// OutputStream outputStream = new FileOutputStream("C:\\log\\" +
				// media.getName());
			
				java.io.InputStream inputStream = media;
				byte[] buffer = new byte[1024];
				for (int count; (count = inputStream.read(buffer)) != -1;) {
					outputStream.write(buffer, 0, count);
				}
				outputStream.flush();
				outputStream.close();
				inputStream.close();
				retorno = 1;
				return retorno;
			}

	protected String getFileMediaName() {
		return getDirectoryMedia(true)
				//+ this.getMedia().getName();
				+ this.getFileName();
	}

	/**
	 * Metódo que remove a Media tual do disco
	 * 
	 * @return 1 se a Media for apagada do disco com sucesso 2 se a Media não
	 *         existe(no diretorio do item taxonomico e estação) 0 se houve
	 *         errao ao remover a Media;
	 */
	public int deleteMediaFromDisk() {
		int retorno = 0;
	
		File file = new File(getDirectoryMedia(false));
		if (!file.exists()) {
			return 2;
		}
	
		String absoluteFilePath = getFileMediaName();		
		retorno = deleteFileFromDisk(absoluteFilePath);			
		
		return retorno;
	}

	/**
	 * @param absoluteFilePath
	 * @return retorna 1 para sucesso, 0 para erro ao apagar, 2 caso o arquivo n�o exista.
	 */
	protected int deleteFileFromDisk(String absoluteFilePath) {
		int retorno=0;
		File fileMedia = new File(absoluteFilePath);
		
		if (fileMedia.exists()) {
			if (fileMedia.delete()) {
				retorno = 1;
			} else {
				retorno = 0;
			}
	
		}else{
			retorno = 2;
		}
		return retorno;
	}

	/**
	 * Metódo que grava a Media tual no disco
	 * 
	 * @return 1 se a Media for grada em disco com sucesso 2 se a Media já
	 *         existe(no diretorio do item taxonomico e estação) 0 se houve
	 *         errao ao gravar a Media;
	 */
	public int writeMediaToDisk() {
		int retorno = 0;
		try {
	
			File file = new File(getDirectoryMedia(false));
			if (!file.exists()) {
				boolean ok = file.mkdirs();
				if (!ok)
					return 0;
			}
	
			InputStream streamMedia = this.getMedia().getStreamData();
	
			// Nome do arquivo da imgem original
			String absoluteFilePath = getFileMediaName();
			//apaga caso j� exista para substituir
			deleteFileFromDisk(absoluteFilePath);
	
			// grava o arquivo da imagem postada
			retorno = writeInputStreamToDisk(absoluteFilePath, streamMedia);
			
			streamMedia = this.getMedia().getStreamData();
			
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			retorno = 0;
		} catch (IOException e) {
			e.printStackTrace();
			retorno = 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retorno = 0;
		}
		return retorno;
	}

}