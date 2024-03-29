package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.view.zk.composer.TabelaBasicaComposerController;
import br.ueg.portalLab.model.Coletor;

@Component
@Scope("desktop")
public class ColetorComposer extends TabelaBasicaComposerController<Coletor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7013854473654426346L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return Coletor.class;
	}

	@Override
	public Control<Coletor> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

}
