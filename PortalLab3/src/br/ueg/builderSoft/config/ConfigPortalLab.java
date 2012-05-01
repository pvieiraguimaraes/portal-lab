package br.ueg.builderSoft.config;

import br.ueg.builderSoft.util.sets.Config;

public class ConfigPortalLab extends Config {
	
	private static ConfigPortalLab instancia;
	public static ConfigPortalLab getInstancia(){
		if(instancia==null){
			instancia = new ConfigPortalLab();
		}
		return instancia;
	}
	public String getDireitorioImagem(){
		return this.getKey("diretorioImagem");
	}
}
