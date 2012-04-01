package br.ueg.portalLab.view.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ueg.builderSoft.view.managed.TabelasBasicasMB;
import br.ueg.portalLab.model.Coletor;

@ManagedBean
@SessionScoped
public class ColetorMB extends TabelasBasicasMB {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return Coletor.class;
	}
	
	
}
