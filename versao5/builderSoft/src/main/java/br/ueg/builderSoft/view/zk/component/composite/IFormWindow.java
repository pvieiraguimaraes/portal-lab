package br.ueg.builderSoft.view.zk.component.composite;

import br.ueg.builderSoft.view.zk.composer.ComposerController;


/**
 * @author Guiliano
 * interface utilizada para permitir configrar os composer recursivamente com window(janela)
 */
public interface IFormWindow {

	public ComposerController<?> getComposer();
	public void setComposer(ComposerController<?> composer);
}
