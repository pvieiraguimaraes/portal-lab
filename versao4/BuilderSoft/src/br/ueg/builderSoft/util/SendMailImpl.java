/**
 * SendInterestingMail.java - 12/06/2013
 */
package br.ueg.builderSoft.util;

import org.apache.commons.mail.HtmlEmail;

/**
 * 
 *
 * @author Diego Carlos Rezende - Analista de sistemas 
 *         <diego.rezende@unievangelica.edu.br>
 */
public class SendMailImpl extends SendMail {
	
	private String htmlMail;
	private String emailTo;
	
	/**
	 * Construtor SendInterestingMail.java
	 */
	public SendMailImpl(String htmlMail, String emailTo) {
		this.htmlMail = htmlMail;
		this.emailTo = emailTo;
	}

	@Override
	public void send() {
		try {
			HtmlEmail email = initializeEmail();
			email.addBcc(this.emailTo);
			String subject = "Erro no Sistema - " + this.config.getKey("projectName");
			email.setSubject(subject);
			email.setHtmlMsg(this.htmlMail);
			String alternativeMsg = "É necessário habilitar o suporte a  HTML para ler a mensagem!";
			email.setTextMsg(alternativeMsg);
			email.send();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

}
