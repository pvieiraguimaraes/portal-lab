package br.ueg.portalLab.util.control;

import java.util.List;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.ValidatorControl;
import br.ueg.portalLab.model.Usuario;

public class UsuarioValidatorControl extends ValidatorControl {

	public UsuarioValidatorControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
		
	}
	@Override
	public boolean doAction(List<Object> atributes, String action) {
		boolean retorno = super.doAction(atributes, action);
		if(retorno){			
			return this.valideCPF((Usuario) (Entity) atributes.get(0));
		}
		return retorno;
	}
	public boolean valideCPF(Usuario usuario){
		boolean retorno = true;
		if(usuario.getCategoria().getIsCPFObrigatorio()){
			if(usuario.getCPF()==null || (usuario.getCPF()!=null && usuario.getCPF().equals(""))){
				retorno = false;
				messagesControl.addMessageError("usuario_cpf_obrigatorio_categoria");				
			}
		}
		return retorno;
	}

}
