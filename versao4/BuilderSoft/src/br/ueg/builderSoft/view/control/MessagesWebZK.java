package br.ueg.builderSoft.view.control;

import java.util.Locale;

import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.util.constant.MessagesType;
import br.ueg.builderSoft.util.control.MessagesControl;

/**
 * Classe que fica responsavel por exibir as mensagens da visao WEB.
 * 
 * @author Diego/Guiliano
 * 
 */
public class MessagesWebZK extends MessagesControl {
	
	public  static final String ID_WINDOW = "builderSoftMessage";

	public MessagesWebZK() {
		super();
	}

	@Override
	protected Locale getLocale() {

		Locale locale = org.zkoss.util.Locales.getLocale(Library.getProperty(Attributes.PREFERRED_LOCALE));
		if(locale == null){
			locale = org.zkoss.util.Locales.getLocale("pt_BR");
		}
		
		return locale;//FacesContext.getCurrentInstance().getELContext().getLocale();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addMessage(String message, int type) {
		/*FacesContext context = FacesContext.getCurrentInstance();
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
			context.addMessage(null, messageWeb);*/
		//if(type == )
		boolean isError = false;
		String message_type = null;
		//int timeDelay = 10000;
		String position = "center";
		if (type == MessagesType.ERROR) {
			message_type = Messagebox.ERROR;
			isError = true;
		}
		if (type == MessagesType.INFO) {
			message_type = Messagebox.INFORMATION;
		//	timeDelay = 5000;
			position = "top,right";
		}
		if (type == MessagesType.WARNING) {
			message_type = Messagebox.EXCLAMATION;
		}
			//Messagebox.show(message, "", Messagebox.OK, message_type);
		Window w=null;
		try{
			 w = (Window) Executions.getCurrent().getDesktop().getFirstPage().getFirstRoot().getFellow(MessagesWebZK.ID_WINDOW);
		}catch (org.zkoss.zk.ui.ComponentNotFoundException e) {
			//se nao achar ok nao tem problema
		}
		
		if(w==null){
			w = new Window();
			w.setId(MessagesWebZK.ID_WINDOW);
			w.setTitle("Messagens do sistema");
			
			Timer t = new Timer();
			t.setDelay(5000);
			t.setParent(w);
			t.setSclass("window-transparent");
			t.addEventListener("onTimer",new SerializableEventListener() {
				private static final long serialVersionUID = 7868086951980855931L;
				public void onEvent(Event event) throws Exception {
	            	event.getTarget().getParent().setVisible(false); 
	            	event.getTarget().getParent().detach();
	            }
			});
				 
			
			w.setPosition(position);

			w.setClosable(true);
			w.setVisible(true);
			w.setParent(Executions.getCurrent().getDesktop().getFirstPage().getFirstRoot());
			if (isError) {
				w.doModal();
			} else {
				w.doPopup();
			}
		} else {
			w.setPosition(position);
		}
		//w.setWidth("200px");
		Hlayout layout = new Hlayout();
		
		Div typeMessage = new Div();
		typeMessage.setClass(message_type);
		
		layout.appendChild(typeMessage);
		layout.appendChild(new Label(message));

		w.appendChild(layout);
		
		
		
		
	}

}
