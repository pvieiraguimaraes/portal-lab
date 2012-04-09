package br.ueg.builderSoft.util.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractValidatorControl implements SubController{

	protected MessagesControl messagesControl;
	
	public abstract boolean doAction(HashMap<String, Object> mapFields, String action);

	protected int orderValidate = 0;
	protected List<String> actionsToValidate = new ArrayList<String>();
	
	
	public AbstractValidatorControl(MessagesControl pMessagesControl, int orderValidate){
		this.messagesControl = pMessagesControl;
		this.orderValidate = orderValidate;
		this.actionsToValidate.add("SAVE");
	}
	public AbstractValidatorControl(MessagesControl pMessagesControl, int orderValidate, List<String> actionsToValidate){
		this.messagesControl = pMessagesControl;
		this.orderValidate = orderValidate;
		this.setActionsToValidate(actionsToValidate);
	}


	public AbstractValidatorControl() {
		super();
	}

	/**
	 * @return the orderValidate
	 */
	public int getOrderValidate() {
		return orderValidate;
	}

	/**
	 * @param orderValidate the orderValidate to set
	 */
	public void setOrderValidate(int orderValidate) {
		this.orderValidate = orderValidate;
	}

	/**
	 * @return the actionsToValidate
	 */
	public List<String> getActionsToValidate() {
		return actionsToValidate;
	}

	/**
	 * @param actionsToValidate the actionsToValidate to set
	 */
	public void setActionsToValidate(List<String> actionsToValidate) {
		this.actionsToValidate = actionsToValidate;
	}

	public boolean isValidateAction(String action) {		
		if(actionsToValidate!=null && actionsToValidate.contains(action)) return true;
		return false;
	}

}