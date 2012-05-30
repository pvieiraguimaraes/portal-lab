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
			String contextName = Executions.getCurrent().getDesktop().getWebApp().getServletContext().getContextPath();
			realPath = realPath.replace(contextName, "");
			instancia.setRootApplicationPath(realPath);
		}
		return instancia;
	}
	public String getDireitorioMedia(){
		return this.getKey("diretorioMedia");
	}
	
	public String getRootApplicationPath() {
		return rootApplicationPath;
	}
	public void setRootApplicationPath(String rootApplicationPath) {
		this.rootApplicationPath = rootApplicationPath;
	}
}
