package br.ueg.portalLab.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.persistence.DiscriminatorValue;

import org.zkoss.image.AImage;
import org.zkoss.image.Image;
@javax.persistence.Entity
@DiscriminatorValue("imagem")
public class EspecieImagem extends EspecieMultimidia<Image> {

	private static final long serialVersionUID = 1962064058618011238L;
	public EspecieImagem(String caminho){
		this.setCaminho(caminho);		
	}
	public EspecieImagem(){
		
	}

	@Override
	public Image getFileFromCaminho() {
		if(this.getCaminho()!=null && this.getCaminho().length()>0){
			

			try {
				media = new AImage(this.getCaminho());

			} catch (IOException e) {
				System.err.println(e.getCause());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.media;
	}

}
