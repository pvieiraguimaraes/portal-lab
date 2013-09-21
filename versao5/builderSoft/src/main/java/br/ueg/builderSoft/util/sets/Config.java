package br.ueg.builderSoft.util.sets;

import java.util.ResourceBundle;

/**
 * Classe para servir de base para as configuraçães do sistema. 
 * banco de dados
 * @author Guiliano
 *
 */
public class Config{
	
	private ResourceBundle config;
	
	public Config(){
		config = ResourceBundle.getBundle("br/ueg/builderSoft/config/config");
	}
	public String getKey(String key){
		return config.getString(key);
	}

}
