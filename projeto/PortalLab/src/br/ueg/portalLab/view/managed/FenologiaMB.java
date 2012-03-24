package br.ueg.portalLab.view.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ueg.portalLab.model.Fenologia;

@ManagedBean
@SessionScoped
public class FenologiaMB extends TabelasBasicasMB {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getTabelaBasica() {
		return Fenologia.class; 
	}
	
	
}
