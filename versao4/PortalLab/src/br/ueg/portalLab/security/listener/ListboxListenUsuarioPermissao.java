package br.ueg.portalLab.security.listener;

import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listitem;

import br.ueg.portalLab.security.model.UsuarioPermissao;

public class ListboxListenUsuarioPermissao implements EventListener<SelectEvent<Listitem, UsuarioPermissao>> {

	private Checkbox usuarioPermissaoCheckBox;
	private UsuarioPermissao usuarioPermissao;
	
	public ListboxListenUsuarioPermissao(Checkbox up){
		this.usuarioPermissaoCheckBox = up;
	}
	@Override
	public void onEvent(SelectEvent<Listitem, UsuarioPermissao> event) throws Exception {
		System.out.println("Object"+event.getSelectedObjects());
		if(event.getSelectedObjects().size()==1){
			for(UsuarioPermissao up : event.getSelectedObjects()){
				usuarioPermissaoCheckBox.setChecked(up.getPermitido());
				this.usuarioPermissao = up;
				usuarioPermissaoCheckBox.addEventListener("onCheck", new CheckboxListenUsuarioPermissao());
			}
		}
	}
	class CheckboxListenUsuarioPermissao implements EventListener<CheckEvent>{
		
		@Override
		public void onEvent(CheckEvent event) throws Exception {
			usuarioPermissao.setPermitido(event.isChecked());						
		}
		
	}

	
}
