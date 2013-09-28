package br.ueg.builderSoft.config;

import org.zkoss.zk.ui.Executions;

import br.ueg.builderSoft.util.sets.Config;


/** Classe de configurações, essa classe tem partes de visão, por isso faz chamadas da 
 * camada do ZK para obter o local de execução
 * @author guiliano
 *
 */
public class ConfigPortalLab extends Config {
	
	private static ConfigPortalLab instancia;
	private String rootApplicationPath="";
	public static ConfigPortalLab getInstancia(){
		if(instancia==null){
			instancia = new ConfigPortalLab();
			String realPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
			System.out.println("realPath:"+realPath);			
			String contextName = Executions.getCurrent().getDesktop().getWebApp().getServletContext().getContextPath();
			String separator = System.getProperty("file.separator");
			/*if(realPath.indexOf(".war")>-1){
				contextName = contextName.concat(".war");
			}
			contextName = separator.concat(contextName.substring(1)).concat(separator);
			
			System.out.println("contextName:"+contextName+"**");
			realPath = realPath.replace(contextName, "");*/
			instancia.setRootApplicationPath(realPath);
		}
		return instancia;
	}
	public String getDireitorioMedia(){
		String diretorio = this.getKey("diretorioMedia");
		/*//TODO procurar por forma de olhar o container
		if(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/").indexOf(".war")>-1){
			diretorio = diretorio.concat(".war");
		}*/
		return diretorio;
	}
	
	public String getRootApplicationPath() {
		return rootApplicationPath;
	}
	public void setRootApplicationPath(String rootApplicationPath) {
		this.rootApplicationPath = rootApplicationPath;
	}
	
	public int getImageWidth(){
		String width = this.getKey("imageWidth");
		int retorno = 300;
		if(width!=null){
			retorno = Integer.parseInt(width);
		}
		return retorno;
	}
	
	/**
	 * @return retorna o caminho dentro da aplica��o web onde estao as medias(imagens, videos, etc)
	 */
	public String getMediaWebPath(){
		return this.getKey("mediaWebPath");
	}
	public int getImageHeight(){
		String height = this.getKey("imageHeight");
		int retorno = 300;
		if(height!=null){
			retorno = Integer.parseInt(height);
		}
		return retorno;
	}

	public int getIntegranteImageWidth(){
		String height = this.getKey("integranteWidth");
		int retorno = 160;
		if(height!=null){
			retorno = Integer.parseInt(height);
		}
		return retorno;
	}
	public int getIntegranteImageHeight(){
		String height = this.getKey("integranteHeight");
		int retorno = 120;
		if(height!=null){
			retorno = Integer.parseInt(height);
		}
		return retorno;
	}
}
