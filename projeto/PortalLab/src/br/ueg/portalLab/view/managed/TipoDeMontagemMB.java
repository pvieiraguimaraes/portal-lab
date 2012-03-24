package br.ueg.portalLab.view.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ueg.portalLab.model.TipoDeMontagem;

@ManagedBean
@SessionScoped
public class TipoDeMontagemMB extends TabelasBasicasMB {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getTabelaBasica() {
		return TipoDeMontagem.class; 
	}
	
	
}
