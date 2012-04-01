package br.ueg.portalLab.view.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ueg.builderSoft.view.managed.TabelasBasicasMB;
import br.ueg.portalLab.model.EstagioDesenvolvimento;
import br.ueg.portalLab.model.TipoDeMontagem;

@ManagedBean
@SessionScoped
public class EstagioDesenvolvimentoMB extends TabelasBasicasMB {
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass(){
		return EstagioDesenvolvimento.class;
	}

}
