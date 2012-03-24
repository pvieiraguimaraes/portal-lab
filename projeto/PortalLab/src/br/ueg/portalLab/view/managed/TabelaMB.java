package br.ueg.portalLab.view.managed;

import br.ueg.portalLab.control.Control;
import br.ueg.portalLab.model.Entity;

public abstract class TabelaMB extends MB<Entity> {

	public TabelaMB() {
		super();
		this.control.setControl(new Control<Entity>(this.control));
	}

	@SuppressWarnings("rawtypes")
	public abstract Class getTabelaBasica();

	@Override
	protected Entity initializeEntity() {
		Entity retorno = null;
		try {
			retorno =  (Entity) getTabelaBasica().newInstance();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

}