package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.builderSoft.view.zk.composer.TabelaBasicaComposerController;
import br.ueg.portalLab.model.Datum;
import br.ueg.portalLab.model.GrupoEnderecoFisico;

@Component
@Scope("desktop")
public class GrupoEnderecoFisicoComposer extends TabelaBasicaComposerController<GrupoEnderecoFisico> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7013854473654426346L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return GrupoEnderecoFisico.class;
	}

}
