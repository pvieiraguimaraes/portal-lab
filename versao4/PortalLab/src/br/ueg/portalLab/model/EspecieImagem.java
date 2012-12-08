package br.ueg.portalLab.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Transient;

import org.jboss.logging.Logger;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;

import br.ueg.builderSoft.config.ConfigPortalLab;
import br.ueg.portalLab.utils.ImageUtil;

@javax.persistence.Entity
@DiscriminatorValue("imagem")
public class EspecieImagem extends EspecieMultimidia<Image> {

	private static final String ESPECIES_DEFAULT_IMG_NAME = "especies.jpg";

	@Transient
	private boolean controleInsercaoPadrao = true;
	
	@Transient
	private Image thumbMedia;

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

	private static final long serialVersionUID = 1962064058618011238L;

	public EspecieImagem(String caminho) {
		this.setNome(caminho);
	}

	public EspecieImagem() {
			this.nome = ESPECIES_DEFAULT_IMG_NAME;
	}

	@Override
	public Image getFileFromCaminho() {
		return getFileFromCaminho(1, this.getNome());
	}
	
	/**
	 * retorna um imagem, podendo ser imagem principal, caso type=1 ou thumb caso type=2
	 * @param type
	 * @return Image a partir do nome do arquivo
	 */
	public Image getFileFromCaminho(int type, String fileName){
		Image imageAux = null;
		InputStream is = null;;
		if (fileName != null && fileName.length() > 0) {

			String path = this.getDirectoryImage(true);
			String aux = "";
			
			try {
				if(type==1){ 
					aux="";				
				}else{
					aux="thumb_";
				}
				if(this.isNew()){
					InputStream streamMedia ;
					if(this.media!=null){
						streamMedia = this.media.getStreamData();
					}else{
						streamMedia = this.getDefaultMedia().getStreamData();
					}
					is = getThumbImageInputStream(streamMedia);
				}else{
					File file = new File(path.concat(aux).concat(fileName));
	
					 is = new FileInputStream(file);
				}
				imageAux = (Image) new AImage(this.getNome(), is);

			} catch (IOException e) {
				System.err.println(e.getCause());
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return imageAux;
	}
	@SuppressWarnings("unchecked")
	public Image getThumbMedia() {
		if(this.thumbMedia!=null){
			return thumbMedia;
		}else{
			thumbMedia = this.getFileFromCaminho(2,this.getNome());
			if(thumbMedia!=null){
				this.setNome(thumbMedia.getName());
			}else{
				thumbMedia = this.getDefaultMedia();
			}
			return thumbMedia;
		}
	}

	@Override
	public Image getDefaultMedia() {
		String diretorioImagem = ConfigPortalLab.getInstancia()
				.getDireitorioMedia();
		String separator = System.getProperty("file.separator");
		/*diretorioImagem = ConfigPortalLab.getInstancia()
				.getRootApplicationPath().concat(separator)
				.concat(diretorioImagem);*/
		System.err.println("diretorioImagem:" + diretorioImagem);

		try {
			media = (Image) new AImage(diretorioImagem.concat(separator)
					.concat(ESPECIES_DEFAULT_IMG_NAME));
		} catch (IOException e) {
			e.printStackTrace();
			media = (Image) new org.zkoss.zul.Image();
		}
		return media;
	}

	/** 
	 * @param endWithSeparator indica se é para adicionar o separador de diretório no final
	 * @return retorna o diretorio onde as imagens devem ser salvas conforme a taxonomia
	 */
	public String getDirectoryImage(boolean endWithSeparator) {
		String separator = System.getProperty("file.separator");

		
		//String diretorioRoot = ConfigPortalLab.getInstancia().getRootApplicationPath();
		String diretorioRoot = ConfigPortalLab.getInstancia().getDireitorioMedia();
		
		String contextPathImage = getContextPathImage(endWithSeparator,false);

		return diretorioRoot
				.concat(separator)
				.concat(contextPathImage);
	}

	/** retorno o path dentro do contexto da aplicação, só que utilizando o 
	 * caracere de separação de diretório do sistema operacional 
	 * e não o separador de url
	 * @param endWithSeparator
	 * @param url indica se é para retornar camiha de url ou de sistema operacional, true-> url, false-> sistema
	 * @return String Caminho do path de imagem
	 */
	public String getContextPathImage(boolean endWithSeparator, boolean url) {
		String separator = System.getProperty("file.separator");
		String diretorioImagem = ConfigPortalLab.getInstancia().getMediaWebPath();
		ItemTaxonomico itemTaxonomico2 = this.getItemTaxonomico();
		String nomeCompleto  = "";
		if(itemTaxonomico2!=null){
			nomeCompleto = itemTaxonomico2.getNomeCompleto();
		}
		String auxPath = url?separator.concat(diretorioImagem):"";
		
		String contextPathImage = auxPath
				.concat(separator)
				.concat(nomeCompleto)
				.concat(separator)
				.concat("imagens")
				.concat(separator)
				.concat(this.getEstacao() != null ? this.getEstacao()
						.getDescricao() : "")
				.concat(endWithSeparator ? separator : "");
		if(url){
			contextPathImage = contextPathImage.replace(separator, "/");
		}
		return contextPathImage;
	}
	
	/**
	 * @return String url da imagem direto da aplicação de media
	 */
	public String getImageURLPath(){
		String auxPath = this.getContextPathImage(true, true);
		auxPath = auxPath.concat(this.getNome());
		return auxPath;
	}

	/**
	 * Esse método devolve a extensão do arquivo representado pelo objeto File
	 * passado como parâmetro.
	 * 
	 * @param file
	 * @return
	 */
	private static String getExtension(String pathfile) {
		String ext = pathfile.substring(pathfile.lastIndexOf('.') + 1);
		return ext;
	}

	/**
	 * Metódo que grava a imagem tual no disco
	 * 
	 * @return 1 se a imagem for grada em disco com sucesso 2 se a imagem já
	 *         existe(no diretorio do item taxonomico e estação) 0 se houve
	 *         errao ao gravar a imagem;
	 */
	/**
	 * @return
	 */
	/**
	 * @return
	 */
	public int writeImagemToDisk() {
		int retorno = 0;
		try {

			File file = new File(getDirectoryImage(false));
			if (!file.exists()) {
				boolean ok = file.mkdirs();
				if (!ok)
					return 0;
			}

			InputStream streamMedia = this.getMedia().getStreamData();

			// Nome do arquivo da imgem original
			String absoluteFilePath = getFileImageName();
			// nome doa rquivo da imagem do thumb
			String absoluteFilePathThumb = getFileThumbImageName();

			// grava o arquivo da imagem postada
			retorno = writeInputStreamToDisk(absoluteFilePath, streamMedia);
			
			streamMedia = this.getMedia().getStreamData();
			InputStream isThumb = getThumbImageInputStream(streamMedia);
			// Grava o arquivo da imagem thumb
			retorno = writeInputStreamToDisk(absoluteFilePathThumb, isThumb);

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

	protected String getFileThumbImageName() {
		return getDirectoryImage(true) + "thumb_"
				+ this.getMedia().getName();
	}

	protected String getFileImageName() {
		return getDirectoryImage(true)
				+ this.getMedia().getName();
	}

	public InputStream getThumbImageInputStream(InputStream streamMedia) throws Exception {
		
		int width = ConfigPortalLab.getInstancia().getImageWidth();
		int height = ConfigPortalLab.getInstancia().getImageHeight();
		
		return ImageUtil.scaleImage(streamMedia, width, height);
	}

	private int writeInputStreamToDisk(String absoluteFilePath,
			InputStream media) throws FileNotFoundException, IOException {
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

	/**
	 * Metódo que remove a imagem tual do disco
	 * 
	 * @return 1 se a imagem for apagada do disco com sucesso 2 se a imagem não
	 *         existe(no diretorio do item taxonomico e estação) 0 se houve
	 *         errao ao remover a imagem;
	 */
	public int deleteImagemFromDisk() {
		int retorno = 0;

		File file = new File(getDirectoryImage(false));
		if (!file.exists()) {
			return 2;
		}

		String absoluteFilePath = getFileImageName();		
		retorno = deleteFileFromDisk(absoluteFilePath);
		
		
		if(retorno == 1){			
			String absoluteFileThumbPath = getFileThumbImageName();	
			retorno = deleteFileFromDisk(absoluteFileThumbPath);
		}
		
		return retorno;
	}

	protected int deleteFileFromDisk( String absoluteFilePath) {
		int retorno=0;
		File fileMedia = new File(absoluteFilePath);
		
		if (fileMedia.exists()) {
			if (fileMedia.delete()) {
				retorno = 1;
			} else {
				retorno = 0;
			}

		}
		return retorno;
	}
	public int getResizedWidth(){
		return ConfigPortalLab.getInstancia().getImageWidth();
	}
}
