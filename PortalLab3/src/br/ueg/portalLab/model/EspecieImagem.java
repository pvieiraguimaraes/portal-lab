package br.ueg.portalLab.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	private boolean controleInsercaoPadrao = true;

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

	}

	@Override
	public Image getFileFromCaminho() {

		if (this.getNome() != null && this.getNome().length() > 0) {

			String path = this.getDirectoryImage(true);
			try {
				File file = new File(path.concat(this.getNome()));

				FileInputStream is = new FileInputStream(file);
				media = (Image) new AImage(this.getNome(), is);

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
		String diretorioImagem = ConfigPortalLab.getInstancia()
				.getDireitorioMedia();
		String separator = System.getProperty("file.separator");
		diretorioImagem = ConfigPortalLab.getInstancia().getRootApplicationPath().concat(separator).concat(diretorioImagem);
		try {
			media = (Image) new AImage(diretorioImagem.concat(
					separator)
					.concat("especies.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			media = (Image) new org.zkoss.zul.Image();
		}
		return media;
	}

	public String getDirectoryImage(boolean endWithSeparator) {
		String separator = System.getProperty("file.separator");

		String diretorioImagem = ConfigPortalLab.getInstancia()
				.getDireitorioMedia();
		String diretorioRoot = ConfigPortalLab.getInstancia().getRootApplicationPath();

		return diretorioRoot
				.concat(separator)
				.concat(diretorioImagem)
				.concat(separator)
				.concat(this.getItemTaxonomico().getNomeCompleto())
				.concat(separator)
				.concat("imagens")
				.concat(separator)
				.concat(this.getEstacao()!=null?this.getEstacao().getDescricao():"")
				.concat(endWithSeparator ? separator : "");
	}
	

	/**
	 * Metódo que grava a imagem tual no disco
	 * 
	 * @return 1 se a imagem for grada em disco com sucesso 2 se a imagem já
	 *         existe(no diretorio do item taxonomico e estação) 0 se houve
	 *         errao ao gravar a imagem;
	 */
	public int writeImagemToDisk() {
		int retorno = 0;
		try {
			
			File file = new File(getDirectoryImage(false));
			if(!file.exists()){
				boolean ok = file.mkdirs();
				if (!ok)
					return 0;
			}

			String absoluteFilePath = getDirectoryImage(true)
					+ this.getMedia().getName();
			if (new File(absoluteFilePath).exists())
				return 2;

			OutputStream outputStream = new FileOutputStream(absoluteFilePath);

			// OutputStream outputStream = new FileOutputStream("C:\\log\\" +
			// media.getName());

			java.io.InputStream inputStream = this.getMedia().getStreamData();
			byte[] buffer = new byte[1024];
			for (int count; (count = inputStream.read(buffer)) != -1;) {
				outputStream.write(buffer, 0, count);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			retorno = 1;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			if(!file.exists()){
				return 2;
			}

			String absoluteFilePath = getDirectoryImage(true)
					+ this.getMedia().getName();
			File fileMedia = new File(absoluteFilePath);
			if (fileMedia.exists()){
				if(fileMedia.delete()){
					retorno = 1;
				}else{
					retorno = 0;
				}
				
			}

		return retorno;
	}
}
