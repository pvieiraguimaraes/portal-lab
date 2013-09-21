package br.ueg.builderSoft.view.zk.composer;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;

@SuppressWarnings("serial")
public abstract class TabelaBasicaComposerController<E extends Entity> extends TabelaComposerController<E> {

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#getNewControl(br.ueg.builderSoft.util.control.MessagesControl)
	 */
	@Override
	public Control<E> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}


	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#getEntityClass()
	 */
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}


	@AttributeView(key = "descricao", isEntityValue = true, fieldType = String.class, caption="tb_descricaoColumn")
	private String fldDescricao;
	
		
	public String getFldDescricao() {
		return fldDescricao;
	}


	public void setFldDescricao(String fldDescricao) {
		this.fldDescricao = fldDescricao;
	}	
}
