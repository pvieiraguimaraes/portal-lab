package br.ueg.portalLab.util.sets;

import java.util.ResourceBundle;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Classe utilizada pelo Spring para gerar a conexão com o 
 * banco de dados
 * @author Diego
 *
 */
public class Connect extends DriverManagerDataSource{
	
	private ResourceBundle jdbc;
	
	public Connect() {
		jdbc = ResourceBundle.getBundle("br/ueg/portalLab/bundle/jdbc");
		this.setDriverClassName(jdbc.getString("driverClassName"));
		this.setUrl(jdbc.getString("url"));
		this.setUsername(jdbc.getString("username"));
		this.setPassword(jdbc.getString("password"));
	}

}
