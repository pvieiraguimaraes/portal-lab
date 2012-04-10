package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.builderSoft.view.zk.composer.TabelaBasicaComposerController;
import br.ueg.portalLab.model.Determinador;

@Component
@Scope("desktop")
public class DeterminadorComposer extends TabelaBasicaComposerController<Determinador> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7013854473654426346L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return Determinador.class;
	}

}
