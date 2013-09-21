package br.ueg.portalLab.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.jboss.logging.Logger;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;

import br.ueg.builderSoft.config.ConfigPortalLab;
import br.ueg.portalLab.utils.ImageUtil;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class SuperEspecieImagem<TYPE extends Media> extends EspecieMultimidia<TYPE> {
	

	@Transient
	protected TYPE thumbMedia;

	private static final long serialVersionUID = 1962064058618011238L;

	public SuperEspecieImagem(String caminho) {
		this.setFileName(caminho);
	}
	
	

	/* (non-Javadoc)
	 * @see br.ueg.portalLab.model.EntityMedia#getDefaultMediaName()
	 */
	@Override
	public String getDefaultMediaName() {
		return  "especies.jpg";
	}



	public SuperEspecieImagem() {
			this.fileName = this.getDefaultMediaName();
	}

	@Override
	public TYPE getFileFromCaminho() {
		return getFileFromCaminho(1, this.getFileName());
	}
	
	/**
	 * retorna um imagem, podendo ser imagem principal, caso type=1 ou thumb caso type=2
	 * @param type
	 * @return Image a partir do nome do arquivo
	 */
	public TYPE getFileFromCaminho(int type, String fileName){
		TYPE imageAux = null;
		InputStream is = null;;
		if (fileName != null && fileName.length() > 0) {

			String path = this.getDirectoryMedia(true);
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
				imageAux = (TYPE) new AImage(this.getFileName(), is);

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
	public TYPE getThumbMedia() {
		if(this.thumbMedia!=null){
			return thumbMedia;
		}else{
			thumbMedia = this.getFileFromCaminho(2,this.getFileName());
			if(thumbMedia!=null){
				this.setFileName(thumbMedia.getName());
			}else{
				thumbMedia = this.getDefaultMedia();
			}
			return thumbMedia;
		}
	}

	@Override
	public TYPE getDefaultMedia() {
		String diretorioImagem = ConfigPortalLab.getInstancia()
				.getDireitorioMedia();
		String separator = System.getProperty("file.separator");
		/*diretorioImagem = ConfigPortalLab.getInstancia()
				.getRootApplicationPath().concat(separator)
				.concat(diretorioImagem);*/
		System.err.println("diretorioImagem:" + diretorioImagem);

		try {
			media = (TYPE) new AImage(diretorioImagem.concat(separator)
					.concat(this.getDefaultMediaName()));
		} catch (IOException e) {
			e.printStackTrace();
			media = (TYPE) new org.zkoss.zul.Image();
		}
		return media;
	}

	
	

	

	/**
	 * Metódo que grava a imagem tual no disco e o thumb da imagem
	 * 
	 * @return 1 se a imagem for grada em disco com sucesso 2 se a imagem já
	 *         existe(no diretorio do item taxonomico e estação) 0 se houve
	 *         errao ao gravar a imagem;
	 */	
	@Override
	public int writeMediaToDisk() {
		int retorno = 0;		
		if(super.writeMediaToDisk()==1){
			try {					
				// nome doa rquivo da imagem do thumb
				String absoluteFilePathThumb = getFileThumbImageName();
				
				InputStream streamMedia = this.getMedia().getStreamData();
								
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
		}
		return retorno;
	}

	protected String getFileThumbImageName() {
		return getDirectoryMedia(true) + "thumb_"
				+ this.getMedia().getName();
	}

	

	public InputStream getThumbImageInputStream(InputStream streamMedia) throws Exception {
		
		int width = ConfigPortalLab.getInstancia().getImageWidth();
		int height = ConfigPortalLab.getInstancia().getImageHeight();
		
		return ImageUtil.scaleImage(streamMedia, width, height);
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
		retorno = super.deleteMediaFromDisk(); 		
		if(retorno == 1){			
			String absoluteFileThumbPath = getFileThumbImageName();	
			retorno = deleteFileFromDisk(absoluteFileThumbPath);
		}
		
		return retorno;
	}	
	
	public int getResizedWidth(){
		return ConfigPortalLab.getInstancia().getImageWidth();
	}
}
