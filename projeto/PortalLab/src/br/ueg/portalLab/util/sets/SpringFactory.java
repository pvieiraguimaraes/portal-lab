package br.ueg.portalLab.util.sets;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Classe que carrega as configura��es do Spring
 * Atrav�s dela que se faz a inje��o de depend�ncias
 * @author Diego
 *
 */
public class SpringFactory extends ClassPathXmlApplicationContext {

	private static ClassPathXmlApplicationContext instance = null;
	
	/**
	 * @return Instancia do Spring com informa�oes do xml lidas
	 */
	public static ClassPathXmlApplicationContext getInstance() {
		if (instance == null) {
			instance = new ClassPathXmlApplicationContext("br/ueg/portalLab/util/sets/spring.xml");
		} 
		return instance;
	}
	
	private SpringFactory() {}	
	
}
