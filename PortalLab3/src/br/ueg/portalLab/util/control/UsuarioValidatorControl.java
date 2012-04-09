package br.ueg.portalLab.util.control;

import java.util.HashMap;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.ValidatorControl;
import br.ueg.portalLab.model.Usuario;

public class UsuarioValidatorControl extends ValidatorControl {

	public UsuarioValidatorControl(MessagesControl pMessagesControl, int orderValidate) {
		super(pMessagesControl,orderValidate);
		
	}
	@Override
	public boolean doAction(HashMap<String, Object> mapFields, String action) {
		
		return this.valideCPF((Usuario) (Entity) mapFields.get("entity"));
	}
	public boolean valideCPF(Usuario usuario){
		boolean retorno = true;
		if(usuario.getCategoria()!=null && usuario.getCategoria().getIsCPFObrigatorio()){
			if(usuario.getCPF()==null || (usuario.getCPF()!=null && usuario.getCPF().equals(""))){
				retorno = false;
				messagesControl.addMessageError("usuario_cpf_obrigatorio_categoria");				
			}
		}
		return retorno;
	}

}
