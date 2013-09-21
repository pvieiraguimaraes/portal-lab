package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.builderSoft.view.zk.composer.TabelaBasicaComposerController;
import br.ueg.portalLab.model.MetodoColeta;
import br.ueg.portalLab.model.TipoDeMontagem;

@Component
@Scope("desktop")
public class MetodoComposer extends TabelaBasicaComposerController<MetodoColeta> {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return MetodoColeta.class;
	}

}
