package br.ueg.builderSoft.view.zk.composer;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.model.Entity;

@SuppressWarnings("serial")
public abstract class TabelaComposerController<E extends Entity> extends ComposerController<E> {
	
	
	//para acesso ao formulário de edição
	@Wire
	protected Window crudFormWindow;


	@Override
	public Window getEditForm() {
		return this.crudFormWindow;
	}

	@Override
	public void setEditForm(Window form) {
		this.crudFormWindow  =  form;
	}

}
