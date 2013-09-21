package br.ueg.portalLab.util.control;

import java.util.HashMap;
import java.util.List;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.ValidatorControl;
import br.ueg.portalLab.model.Usuario;

public class UsuarioValidatorControl extends ValidatorControl {


	public UsuarioValidatorControl(MessagesControl pMessagesControl, int orderValidate, List<String> actionsToValidate) {
		super(pMessagesControl, orderValidate, actionsToValidate);
	}
	public UsuarioValidatorControl(MessagesControl pMessagesControl, int orderValidate) {
		super(pMessagesControl, orderValidate);
	}
	
	@Override
	public boolean doAction(HashMap<String, Object> mapFields, String action) {
		Boolean valideCPF = this.valideCPF((Usuario) (Entity) mapFields.get("entity"));
		Boolean valideSenha = this.valideSenha((Usuario) (Entity) mapFields.get("entity"), action);
		return (valideCPF && valideSenha);
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
	
	public boolean valideSenha(Usuario usuario, String action){
		boolean retorno = true;
		if(usuario.isNew()){
			if(usuario.getSenha() == null || usuario.getSenha().equals("")){
				retorno = false;
				messagesControl.addMessageError("usuario_senha");
			}
			if(usuario.getSenha() == null || usuario.getSenha().length() < 5){
				retorno = false;
				messagesControl.addMessageError("usuario_senha_tamanho");
			}
		}
		return retorno;
	}

}
