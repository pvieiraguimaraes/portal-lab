package br.ueg.portalLab.util.control;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import br.ueg.portalLab.util.constant.MessagesType;

/**
 * Classe que controla as mensagem.
 * 
 * @author Diego
 * 
 */
public abstract class MessagesControl implements SubController{

	private ResourceBundle messages;

	public MessagesControl() {
		messages = ResourceBundle.getBundle(
				"br/ueg/portalLab/bundle/messages", this.getLocale());
	}

	@Override
	public boolean doAction(List<Object> atributes, String action) {
		if (action.equalsIgnoreCase("success")) {
			addMessageSuccess(action);
		} else {
			addMessageCancel(action);
		}
		return true;
	}

	/**
	 * M�todo que retorna a mensagem correspondente a vari�vel enviada
	 * 
	 * @param typeMessage
	 *            , vari�vel da mensagem
	 * @return a mensage correspondente a v�riavel.
	 */
	protected String getMessage(String typeMessage) {
		return messages.getString(typeMessage);
	}

	/**
	 * M�todo que adiciona uma mensagem de erro
	 * 
	 * @param key
	 *            chave que representa a mensagem no bundle
	 */
	public void addMessageError(String key) {
		addMessage(getMessage("erro_" + key), MessagesType.ERROR);
	}

	/**
	 * M�todo que adiciona uma mensagem de sucesso
	 * 
	 * @param key
	 *            chave que representa a mensagem no bundle
	 */
	private void addMessageSuccess(String key) {
		addMessage(getMessage(key), MessagesType.INFO);
	}

	/**
	 * M�todo que adiciona uma mensagem de cancelamento
	 * 
	 * @param key
	 *            chave que representa a mensagem no bundle
	 */
	private void addMessageCancel(String key) {
		addMessage(getMessage(key), MessagesType.WARNING);
	}

	/**
	 * M�todo que retorna a Locale do bundle
	 * 
	 * @return locale do bundle, ex: en_US, pt_BR
	 */
	protected abstract Locale getLocale();

	/**
	 * M�todo que adiciona a mensagem � vis�o
	 * 
	 * @param message
	 *            mensagem que ser� exibida
	 * @param type
	 *            corresponde ao tipo de mensagem (�cone da mensagem)
	 */
	public abstract void addMessage(String message, int type);

}