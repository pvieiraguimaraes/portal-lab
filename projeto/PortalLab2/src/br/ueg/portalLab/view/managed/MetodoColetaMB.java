package br.ueg.portalLab.view.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ueg.builderSoft.view.managed.TabelasBasicasMB;
import br.ueg.portalLab.model.MetodoColeta;

@ManagedBean
@SessionScoped
public class MetodoColetaMB extends TabelasBasicasMB {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getTabelaBasica() {
		return MetodoColeta.class; 
	}
	
	
}
