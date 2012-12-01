package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.view.zk.composer.TabelaBasicaComposerController;
import br.ueg.portalLab.model.EstagioDesenvolvimento;

@Component
@Scope("desktop")
public class EstagioDesenvolvimentoComposer extends TabelaBasicaComposerController<EstagioDesenvolvimento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7013854473654426346L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return EstagioDesenvolvimento.class;
	}

	@Override
	public Control<EstagioDesenvolvimento> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

}
