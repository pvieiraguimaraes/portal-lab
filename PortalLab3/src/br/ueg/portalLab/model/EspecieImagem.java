package br.ueg.portalLab.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Transient;

import org.zkoss.image.AImage;
import org.zkoss.image.Image;

import br.ueg.builderSoft.config.ConfigPortalLab;
@javax.persistence.Entity
@DiscriminatorValue("imagem")
public class EspecieImagem extends EspecieMultimidia<Image> {

	@Transient
	private boolean controleInsercaoPadrao=true;
	@Override
	public boolean isNew() {
		if(this.isControleInsercaoPadroa()){
			return super.isNew();
		}else{
			return true;
		}
	}
	/**
	 * @return the controleInsercaoPadroa
	 */
	public boolean isControleInsercaoPadroa() {
		return controleInsercaoPadrao;
	}
	public boolean getControleInsercaoPadrao(){
		return this.isControleInsercaoPadroa();
	}

	/**
	 * @param controleInsercaoPadroa the controleInsercaoPadroa to set
	 */
	public void setControleInsercaoPadroa(boolean controleInsercaoPadroa) {
		this.controleInsercaoPadrao = controleInsercaoPadroa;
	}

	private static final long serialVersionUID = 1962064058618011238L;
	public EspecieImagem(String caminho){
		this.setNome(caminho);		
	}
	public EspecieImagem(){
		
	}

	@Override
	public Image getFileFromCaminho() {
		String path = ConfigPortalLab.getInstancia().getDireitorioImagem().concat(System.getProperty("file.separator"));
		if(this.getNome()!=null && this.getNome().length()>0){
			

			try {
				File file = new File(path.concat(this.getNome()));
				
				FileInputStream is = new FileInputStream ( file);
				media =(Image) new AImage(this.getNome(),is);

			} catch (IOException e) {
				System.err.println(e.getCause());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.media;
	}
	@Override
	public Image getDefaultMedia() {
		String diretorioImagem = ConfigPortalLab.getInstancia().getDireitorioImagem();
		try {
			media = (Image) new AImage(diretorioImagem.concat(System.getProperty("file.separator")).concat("especies.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			media = (Image) new org.zkoss.zul.Image();
		}
		return media;
	}

}
