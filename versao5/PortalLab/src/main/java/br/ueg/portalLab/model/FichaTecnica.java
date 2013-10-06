package br.ueg.portalLab.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Transient;

import br.ueg.builderSoft.config.ConfigPortalLab;
import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.portalLab.utils.ImageUtil;
import org.zkoss.image.Image;


@javax.persistence.Entity
public class FichaTecnica extends SuperEspecieImagem<Image> {
	
	@Attribute(Required = false, SearchField = false)
	@Transient
	protected Image mediaVerso;
	
	/**
	 * O caminho incui o caminho completo, isso � 
	 * inclui o nome do arquivo de midia
	 */
	@Column(name="caminhoVerso", length=1000, nullable=true)
	@Attribute(Required = false, SearchField=true)
	protected String fileNameVerso;
	
	@Transient
	protected Image thumbMediaVerso;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1545857512750696261L;

	/* (non-Javadoc)
	 * @see br.ueg.portalLab.model.EspecieMultimidia#getTypeMediaSimpleName()
	 */
	@Override
	public String getTypeMediaSimpleName() {
		return "ficha_tecnica";
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.portalLab.model.EntityMedia#getDefaultMediaName()
	 */
	@Override
	public String getDefaultMediaName() {
		return  "ficha_tecnica.jpg";
	}
	@Override
	public InputStream getThumbImageInputStream(InputStream streamMedia) throws Exception {
		
		int width = ConfigPortalLab.getInstancia().getFichaTecnicaWidth();
		int height = ConfigPortalLab.getInstancia().getFichaTecnicaHeight();
		
		return ImageUtil.scaleImage(streamMedia, width, height);
	}
	public Image getFileVersoFromCaminho() {
		return getFileFromCaminho(1, this.getFileNameVerso(), this.mediaVerso);
	}

	public Image getMediaVerso() {
		if(this.mediaVerso!=null){
			return mediaVerso;
		}else{
			mediaVerso = this.getFileVersoFromCaminho();
			if(mediaVerso !=null){
				this.setFileNameVerso(mediaVerso.getName());
			}else{
				mediaVerso = this.getDefaultMedia();
			}
			return mediaVerso;
		}
	}

	public void setMediaVerso(Image media) {
		if(mediaVerso!=null){
			this.setFileNameVerso(mediaVerso.getName());
		}
		this.mediaVerso = media;
	}

	public String getFileNameVerso() {
		return fileNameVerso;
	}

	public void setFileNameVerso(String fileNameVerso) {
		this.fileNameVerso = fileNameVerso;
	}

	public Image getThumbMediaVerso() {
/*
		//if(this.thumbMediaVerso!=null ){
		//	return thumbMediaVerso;
		//}else{
			thumbMediaVerso = this.getFileFromCaminho(2,this.getFileNameVerso());
			if(thumbMediaVerso!=null){
				//this.setFileNameVerso(thumbMediaVerso.getName());
			}else{
				if(this.mediaVerso!=null){
					thumbMediaVerso = this.getMediaVerso();
				}else{
					thumbMediaVerso = this.getDefaultMedia();
				}
			}
			return thumbMediaVerso;
		//}*/
		//if(this.thumbMediaVerso!=null){
		//	return thumbMediaVerso;
		//}else{
			thumbMediaVerso = this.getFileFromCaminho(2,this.getFileNameVerso(), this.mediaVerso);//this.getFileVersoFromCaminho();
			if(thumbMediaVerso==null){
				
				thumbMediaVerso = this.getDefaultMedia();
			}
			return thumbMediaVerso;
		//}

	}

	public void setThumbMediaVerso(Image thumbMediaVerso) {
		this.thumbMediaVerso = thumbMediaVerso;
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
				String absoluteFilePathVersoThumb = getFileThumbVersoImageName();
				String absoluteFilePathVerso = getFileMediaVersoName();
				
								
				InputStream streamMediaVerso = this.getMediaVerso().getStreamData();
				
				retorno = writeInputStreamToDisk(absoluteFilePathVerso, streamMediaVerso);
				if(retorno == 1){
					streamMediaVerso = this.getMediaVerso().getStreamData();
					
					InputStream isThumb = getThumbImageInputStream(streamMediaVerso);
					// Grava o arquivo da imagem thumb
					retorno = writeInputStreamToDisk(absoluteFilePathVersoThumb, isThumb);
				}
				
	
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
	protected String getFileThumbVersoImageName() {
		return getDirectoryMedia(true) + "thumb_"
				+ this.getMediaVerso().getName();
	}
	protected String getFileMediaVersoName() {
		return getDirectoryMedia(true)
				//+ this.getMedia().getName();
				+ this.getFileNameVerso();
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
		if(retorno == 1 || retorno ==2){
			String absoluteFilePathVersoThumb = getFileThumbVersoImageName();
			String absoluteFilePathVerso = getFileMediaVersoName();
				
			retorno = deleteFileFromDisk(absoluteFilePathVersoThumb);
			if(retorno == 1 || retorno ==2){
				retorno = deleteFileFromDisk(absoluteFilePathVerso);
			}
		}
		
		return retorno;
	}

}
