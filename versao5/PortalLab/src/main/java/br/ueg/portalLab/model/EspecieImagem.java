package br.ueg.portalLab.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Column;

import br.ueg.builderSoft.config.ConfigPortalLab;
import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.portalLab.utils.ImageUtil;

@javax.persistence.Entity
public class EspecieImagem<Image> extends SuperEspecieImagem {

	@Attribute(Required=false, SearchField = true)
	@Column(name="representativa")
	private Boolean representativa = new Boolean(false);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1962064058618011238L;

	@Override
	public String getTypeMediaSimpleName() {
		return "image";
	}

	public Boolean getRepresentativa() {
		return representativa;
	}

	public void setRepresentativa(Boolean representativa) {
		this.representativa = representativa;
	}
	private String getRepresentativeThumbName(){
		return getDirectoryMedia(true) + "rep_thumb_"	+ this.getMedia().getName();
	}

	@Override
	public int writeMediaToDisk() {
		int retorno = 0;		
		if(super.writeMediaToDisk()==1){
			if(this.getRepresentativa()){
				try {
					
					// nome doa rquivo da imagem do thumb
					String absoluteFilePathThumb = this.getRepresentativeThumbName();
					
					InputStream streamMedia = this.getMedia().getStreamData();
									
					InputStream isThumb = getThumbImageInputStreamRepresentativa(streamMedia);
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
		}
		return retorno;
	}

	@Override
	public int deleteMediaFromDisk() {
		int retorno = 0;
		retorno = super.deleteMediaFromDisk(); 		
		if(retorno == 1){
			if(this.getRepresentativa()){
				String absoluteFileThumbPath = this.getRepresentativeThumbName();
				retorno = deleteFileFromDisk(absoluteFileThumbPath);
			}
		}
		
		return retorno;
	}
	
	public InputStream getThumbImageInputStreamRepresentativa(InputStream streamMedia) throws Exception {
		
		int width = ConfigPortalLab.getInstancia().getImageRepresentativaWidth();
		int height = ConfigPortalLab.getInstancia().getImageRepresentativaHeigth();
		
		return ImageUtil.scaleImage(streamMedia, width, height);
	}
}
