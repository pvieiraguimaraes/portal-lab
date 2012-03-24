package br.ueg.portalLab.util.sets;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Classe que carrega as configurações do Spring
 * Através dela que se faz a injeção de dependências
 * @author Diego
 *
 */
public class SpringFactory extends ClassPathXmlApplicationContext {

	private static ClassPathXmlApplicationContext instance = null;
	
	/**
	 * @return Instancia do Spring com informaçoes do xml lidas
	 */
	public static ClassPathXmlApplicationContext getInstance() {
		if (instance == null) {
			instance = new ClassPathXmlApplicationContext("br/ueg/portalLab/util/sets/spring.xml");
		} 
		return instance;
	}
	
	private SpringFactory() {}	
	
}
