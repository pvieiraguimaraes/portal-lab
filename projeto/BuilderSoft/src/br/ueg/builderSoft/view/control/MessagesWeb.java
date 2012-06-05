package br.ueg.builderSoft.view.control;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import br.ueg.builderSoft.util.constant.MessagesType;
import br.ueg.builderSoft.util.control.MessagesControl;

/**
 * Classe que fica responsavel por exibir as mensagens da visao WEB.
 * 
 * @author Diego
 * 
 */
public class MessagesWeb extends MessagesControl {

	public MessagesWeb() {
		super();
	}

	@Override
	protected Locale getLocale() {
		return FacesContext.getCurrentInstance().getELContext().getLocale();
	}

	@Override
	public void addMessage(String message, int type) {
		FacesContext context = FacesContext.getCurrentInstance();
			Severity severity = null;
			if (type == MessagesType.ERROR) {
				severity = FacesMessage.SEVERITY_ERROR;
			}
			if (type == MessagesType.INFO) {
				severity = FacesMessage.SEVERITY_INFO;
			}
			if (type == MessagesType.WARNING) {
				severity = FacesMessage.SEVERITY_WARN;
			}
			FacesMessage messageWeb = new FacesMessage(severity, "", message);
			context.addMessage(null, messageWeb);
	}

}
