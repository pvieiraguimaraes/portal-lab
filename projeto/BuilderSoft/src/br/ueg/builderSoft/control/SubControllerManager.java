package br.ueg.builderSoft.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.control.IListingControl;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.control.ValidatorControl;

public class SubControllerManager<E extends Entity> {
	private List<SubController> subControllers;
	
	public SubControllerManager(){
		this.subControllers = new ArrayList<SubController>();
	}
	

	/**
	 * Adiciona um subcontrollador ao manager, se for do tipo validator pode ser adicionados v�rios,
	 * dos demais n�o � poss�vel adicionar mais de um
	 * @param subController
	 * @return
	 */
	public boolean addController(SubController subController) {
		boolean retorno = false;
		
		if((subController instanceof ValidatorControl )){			
			retorno = true;
		}else if(this.getController(subController.getClass())==null){			
			retorno = true;
		}
		
		if(retorno){
			this.subControllers.add(subController);
		}
		
		return retorno;
	}
	/**
	 * M�todo que retorna um subControlador da lista se tiver a mesma assinatura que a classe
	 * enviado por par�metro
	 * @param pClass, .class que deseja analisar se cont�m na lista.
	 * @return o subControlador desejado ou null caso n�o seja encontrado.
	 */
	@SuppressWarnings("rawtypes")
	public SubController getController(Class pClass) {
		SubController result = null;
		for (int i = 0; i < subControllers.size(); i++) {
			SubController subController = subControllers.get(i);
			if (pClass.isInstance(subController)) {
				result = subController;
				break;
			}
		}
		return result;
	}
	
	/**
	 * @return retorna o MessagesControl se esse tiver sido definido
	 */
	public MessagesControl getMessagesControl(){
		return (MessagesControl) this.getController(MessagesControl.class);
	}
	
	/**
	 * @return retorna o ListintControl se esse tiver sido definido;
	 */
	@SuppressWarnings("unchecked")
	public IListingControl<E> getListingControl(){
		return (IListingControl<E>) this.getController(IListingControl.class);
	} 
	
	public List<ValidatorControl> getValidatorControls(String action){
		List<ValidatorControl> validators= new ArrayList<ValidatorControl>();
		
		for (int i = 0; i < subControllers.size(); i++) {
			SubController subController = subControllers.get(i);
			if (ValidatorControl.class.isInstance(subController)  && (((ValidatorControl)subController).isValidateAction(action)) ) {
				validators.add((ValidatorControl) subController);
			}
		}
		Collections.sort(validators, new Comparator<ValidatorControl>(){
			    public int compare( ValidatorControl v1, ValidatorControl v2 ) {
			      return v1.getOrderValidate()-v2.getOrderValidate();
			    }
			  });
		
		return validators;
	}
}
