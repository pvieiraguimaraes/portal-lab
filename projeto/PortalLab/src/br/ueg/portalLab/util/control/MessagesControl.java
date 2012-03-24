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
	 * Método que retorna a mensagem correspondente a variável enviada
	 * 
	 * @param typeMessage
	 *            , variável da mensagem
	 * @return a mensage correspondente a váriavel.
	 */
	protected String getMessage(String typeMessage) {
		return messages.getString(typeMessage);
	}

	/**
	 * Método que adiciona uma mensagem de erro
	 * 
	 * @param key
	 *            chave que representa a mensagem no bundle
	 */
	public void addMessageError(String key) {
		addMessage(getMessage("erro_" + key), MessagesType.ERROR);
	}

	/**
	 * Método que adiciona uma mensagem de sucesso
	 * 
	 * @param key
	 *            chave que representa a mensagem no bundle
	 */
	private void addMessageSuccess(String key) {
		addMessage(getMessage(key), MessagesType.INFO);
	}

	/**
	 * Método que adiciona uma mensagem de cancelamento
	 * 
	 * @param key
	 *            chave que representa a mensagem no bundle
	 */
	private void addMessageCancel(String key) {
		addMessage(getMessage(key), MessagesType.WARNING);
	}

	/**
	 * Método que retorna a Locale do bundle
	 * 
	 * @return locale do bundle, ex: en_US, pt_BR
	 */
	protected abstract Locale getLocale();

	/**
	 * Método que adiciona a mensagem à visão
	 * 
	 * @param message
	 *            mensagem que será exibida
	 * @param type
	 *            corresponde ao tipo de mensagem (ícone da mensagem)
	 */
	public abstract void addMessage(String message, int type);

}