package br.ueg.builderSoft.util.sets;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.request.SessionScope;

/**
 * Classe que carrega as configuraçães do Spring
 * Através dela que se faz a injeção de dependéncias
 * @author Diego
 *
 */
public class SpringFactory extends ClassPathXmlApplicationContext {

	private static ClassPathXmlApplicationContext instance = null;
	
	/**
	 * @return Instancia do Spring com informações do xml lidas
	 */
	public static ClassPathXmlApplicationContext getInstance() {
		if (instance == null) {
			instance = new ClassPathXmlApplicationContext("br/ueg/builderSoft/config/spring.xml");
			instance.getBeanFactory().registerScope("session", new SessionScope());
		} 
		return instance;
	}
	
	private SpringFactory() {}	
	
}
