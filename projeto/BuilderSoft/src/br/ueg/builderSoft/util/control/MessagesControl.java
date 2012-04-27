package br.ueg.builderSoft.util.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import br.ueg.builderSoft.util.constant.MessagesType;

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
				"br/ueg/builderSoft/config/messages", this.getLocale(), new UTF8Control());
	}

	@Override
	public boolean doAction(HashMap<String, Object> mapFields, String action) {
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
		try{
			return messages.getString(typeMessage);	
		}catch(java.util.MissingResourceException e){
			e.printStackTrace();
		}
		return typeMessage;
	}
	
	public boolean existsMessage(String typeMessage){
		boolean exist = false;
		try{			
			messages.getString(typeMessage);
			exist = true;
		}catch (Exception e) {

		}
		return exist;
	}
	public boolean existsErrorMessage(String typeMessage){
		return this.existsMessage("erro_".concat(typeMessage));
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
	
	class UTF8Control extends Control {
	    public ResourceBundle newBundle
	        (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
	            throws IllegalAccessException, InstantiationException, IOException
	    {
	        // The below is a copy of the default implementation.
	        String bundleName = toBundleName(baseName, locale);
	        String resourceName = toResourceName(bundleName, "properties");
	        ResourceBundle bundle = null;
	        InputStream stream = null;
	        if (reload) {
	            URL url = loader.getResource(resourceName);
	            if (url != null) {
	                URLConnection connection = url.openConnection();
	                if (connection != null) {
	                    connection.setUseCaches(false);
	                    stream = connection.getInputStream();
	                }
	            }
	        } else {
	            stream = loader.getResourceAsStream(resourceName);
	        }
	        if (stream != null) {
	            try {
	                // Only this line is changed to make it to read properties files as UTF-8.
	                bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
	            } finally {
	                stream.close();
	            }
	        }
	        return bundle;
	    }
	}
}