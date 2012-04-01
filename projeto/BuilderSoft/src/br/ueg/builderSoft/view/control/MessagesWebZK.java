package br.ueg.builderSoft.view.control;

import java.util.Locale;

import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;



import br.ueg.builderSoft.util.constant.MessagesType;
import br.ueg.builderSoft.util.control.MessagesControl;

/**
 * Classe que fica responsável por exibir as mensagens da visão WEB.
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
		String message_type = null;
		if (type == MessagesType.ERROR) {
			message_type = Messagebox.ERROR;
		}
		if (type == MessagesType.INFO) {
			message_type = Messagebox.INFORMATION;
		}
		if (type == MessagesType.WARNING) {
			message_type = Messagebox.EXCLAMATION;
		}
			//Messagebox.show(message, "", Messagebox.OK, message_type);
		Window w=null;
		try{
			 w = (Window) Executions.getCurrent().getDesktop().getFirstPage().getFirstRoot().getFellow(this.ID_WINDOW);
		}catch (org.zkoss.zk.ui.ComponentNotFoundException e) {
			//se não achar ok não tem problema
		}
		
		if(w==null){
			w = new Window();
			w.setId(this.ID_WINDOW);
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
				 
			
			w.setPosition("top,right");

			w.setClosable(true);
			w.setVisible(true);
			w.setParent(Executions.getCurrent().getDesktop().getFirstPage().getFirstRoot());
			w.doPopup();
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
