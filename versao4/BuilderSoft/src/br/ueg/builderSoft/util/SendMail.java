/**
 * SendMail.java - 10/05/2013
 */
package br.ueg.builderSoft.util;

import org.apache.commons.mail.HtmlEmail;

import br.ueg.builderSoft.util.sets.Config;

/**
 * 
 *
 * @author Diego Carlos Rezende - Analista de sistemas 
 *         <diego.rezende@unievangelica.edu.br>
 */
public abstract class SendMail extends Thread {
	
	protected Config config;
	
	public SendMail() {
		config = new Config();
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		send();
	}
	
	public abstract void send();
	
	protected HtmlEmail initializeEmail() throws Exception {
		HtmlEmail email = new HtmlEmail();
		email.setHostName(this.config.getKey("email.host"));
		email.setFrom(this.config.getKey("email.from"));
		try {
			email.setAuthentication(this.config.getKey("email.user"), this.config.getKey("email.password"));
			email.setSSLOnConnect(true);
		} catch (Exception e) {
			
		}
		return email;
	}

}
