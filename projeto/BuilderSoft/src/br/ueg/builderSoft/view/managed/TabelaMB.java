package br.ueg.builderSoft.view.managed;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;

/** remover essa classe, ela ficou desnecess�ria
 * @author guiliano
 *
 */
public abstract class TabelaMB extends MB<Entity> {

	public TabelaMB() {
		super();
		//TODO n�o funciona mais ver o do ZK
		//this.control.setControl(new Control<Entity>(this.get));
	}

}