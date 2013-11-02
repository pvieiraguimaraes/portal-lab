package br.ueg.portalLab.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;

import br.ueg.builderSoft.config.ConfigPortalLab;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.portalLab.utils.ImageUtil;
import br.ueg.portalLab.utils.Zipper;

@javax.persistence.Entity
@SuppressWarnings("serial")
@Table(name="jogos_ppt")
public class JogoPPT extends EntityMedia<Media> {

	@Id()
	@GeneratedValue
	@Column(name = "id_jogoppt")
	private Long id;
	
	@Column(name = "nome", nullable=false)
	@Attribute(Required = true, SearchField = true)
	private String nome;
	
	@Column(name = "descricao", length=275, nullable=false)
	@Attribute(Required = true, SearchField = true)
	private String descricao;
	
	@Transient
	private Image thumbMedia;
	
	@Transient
	public static final String DEFAULT_FILE_NAME = "jogos_ppt";
	
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	@Override
	public String getTypeMediaSimpleName() {
		return "jogos_ppt";
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.portalLab.model.EntityMedia#getDefaultMediaName()
	 */
	@Override
	public String getDefaultMediaName() {
		return  "jogos_ppt.zip";
	}
	
	public InputStream getThumbImageInputStream(InputStream streamMedia) throws Exception {
		
		int width = ConfigPortalLab.getInstancia().getIntegranteImageWidth();
		int height = ConfigPortalLab.getInstancia().getIntegranteImageHeight();
		
		return ImageUtil.scaleImage(streamMedia, width, height);
	}

	@Override
	public Media getFileFromCaminho() {
		Media mediaAux = null;
		InputStream is = null;;
		if (fileName != null && fileName.length() > 0) {

			String path = this.getDirectoryMedia(true);
			
			try {				
				if(!this.isNew()){
					//File file = new File(path.concat(fileName));
	
					 //is = new FileInputStream(file);
					InputStream theFile = new FileInputStream(path.concat(fileName));
					ZipInputStream in = new ZipInputStream(theFile);
					
					mediaAux =  new org.zkoss.util.media.AMedia(fileName, null, null, in);
					in.close();
				}
				

			} catch (IOException e) {
				System.err.println(e.getCause());
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mediaAux;
	}



	public Image getThumbMedia() {
		if(this.thumbMedia!=null){
			return thumbMedia;
		}else{
			thumbMedia = this.getImageFromCaminho(2,this.getFileName());
			if(thumbMedia!=null){
				//this.setFileName(thumbMedia.getName());
			}else{
				thumbMedia = (Image) this.getDefaultMedia();
			}
			return thumbMedia;
		}
	}

	public void setThumbMedia(Image thumbMedia) {
		this.thumbMedia = thumbMedia;
	}

	/**
	 * retorna um imagem, podendo ser imagem principal, caso type=1 ou thumb caso type=2
	 * @param type
	 * @return Image a partir do nome do arquivo
	 */
	public Image getImageFromCaminho(int type, String fileName){
		Image imageAux = null;
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
					InputStream streamMedia;

					streamMedia = this.getDefaultMedia().getStreamData();

					is = getThumbImageInputStream(streamMedia);
				}else{
					File file = new File(path.concat(aux).concat(fileName));
	
					 is = new FileInputStream(file);
				}
				imageAux =  (Image) new AImage(/*this.getFileName()*/aux+fileName, is);

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
	
	public Media getDefaultMedia() {
		String diretorioImagem = ConfigPortalLab.getInstancia()
				.getDireitorioMedia();
		String separator = System.getProperty("file.separator");
		Media localMedia = null;

		System.err.println("diretorioImagem:" + diretorioImagem);

		try {
			localMedia = (Media) new AImage(diretorioImagem.concat(separator)
					.concat(this.DEFAULT_FILE_NAME+".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			localMedia = (Media) new org.zkoss.zul.Image();
		}
		return localMedia;
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
				.concat(String.valueOf(this.getId()))
				.concat(separator)
				.concat(endWithSeparator ? separator : "");
		if(url){
			contextPathMedia = contextPathMedia.replace(separator, "/");
		}
		return contextPathMedia;
	}

	@Override
	public int writeMediaToDisk() {
		// TODO Auto-generated method stub
		int writeMediaToDisk = super.writeMediaToDisk();
		if(writeMediaToDisk==1){
			writeJogoPPT();
		}
		return writeMediaToDisk;
	}

	private void writeJogoPPT() {
		String path = "C:\\Users\\Guiliano\\Desktop\\temp\\";
		String file = "Quiz-Hymenoptera.zip";
		
		path = this.getDirectoryMedia(true);
		file = this.getMedia().getName();
		
		String fileSeparator = System.getProperty("file.separator");
		
		String dirInstall = file.replace(".zip", "");

		Zipper zipper = new Zipper();

		File filePath = new File(path.concat(fileSeparator).concat(file));
		File pathInstall = new File(path);

		String path2 = path.concat(fileSeparator).concat(dirInstall);
		
		String files;
		File folder = new File(path2);
		if(folder.exists() && folder.isDirectory()){
			try {
				Zipper.delete(folder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			zipper.extrairZip(filePath, pathInstall);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// http://stackoverflow.com/questions/7532202/regular-expression-for-remove-html-links

		
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				if (files.endsWith(".html") || files.endsWith(".HTML")
						|| files.endsWith(".htm") || files.endsWith(".HTM")) {
					System.out.println(files);

					String oldFileName = path2.concat(fileSeparator).concat(files);
					String tmpFileName = path2.concat(fileSeparator).concat("tmp".concat(files));

					BufferedReader br = null;
					BufferedWriter bw = null;
					try {
						br = new BufferedReader(new FileReader(oldFileName));
						bw = new BufferedWriter(new FileWriter(tmpFileName));
						String line;
						while ((line = br.readLine()) != null) {
							if (line.contains("<a")){
								line = line.replaceAll(
										"<a(\\s[^>]*)?>.*?(</a>)?", "");
								line = line.replace("Primeira página Voltar Continuar Última página Texto", "");
							}
							if (line.contains("</a>")){
								line = line.replace("</a>", "");
								line = line.replace("Primeira página Voltar Continuar Última página Texto", "");
							}
							bw.write(line + "\n");
						}
					} catch (Exception e) {
						e.printStackTrace();
						return;
					} finally {
						try {
							if (br != null)
								br.close();
						} catch (IOException e) {
							//
						}
						try {
							if (bw != null)
								bw.close();
						} catch (IOException e) {
							//
						}
					}
					// Once everything is complete, delete old file..
					File oldFile = new File(oldFileName);
					oldFile.delete();

					// And rename tmp file's name to old file name
					File newFile = new File(tmpFileName);
					newFile.renameTo(oldFile);

				}
			}
		}
	}
}
