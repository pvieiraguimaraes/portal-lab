package br.ueg.builderSoft.view.zk.component.composite;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Div;

/**
 * Classe base para a criação de componentes.
 * 
 * @author leonardo.pessoa
 * 
 */
public abstract class BaseComponent extends Div implements IdSpace {

	private static final long serialVersionUID = -5926796840149453797L;

	private final String DEFAULT_ZUL_FILE_EXTENSION = ".zul";
	
	protected AnnotateDataBinder binder = new AnnotateDataBinder();

	/**
	 * Construtor padrão, é onde se faz a ligação entre os componentes declarados no arquivo .zul e os componentes
	 * utilizados na classe java que representa o componente.
	 */
	public BaseComponent() {
		super();
		try {
			Executions.createComponentsDirectly(getZulReader(), null, this, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Selectors.wireComponents(this, this, false);
		Selectors.wireEventListeners(this, this);
		binder.loadAll();
	}

	/**
	 * Obtém o nome do arquivo zul ao qual o componente está assossiado. Por padrão, o nome adotado para os arquivos zul
	 * dos componentes devem ter o mesmo nome da classe que o representa. Caso decida utilizar um nome diferente, este
	 * método deve ser sobreescrito na classe que herdar esta.
	 * 
	 * @return
	 */
	protected Reader getZulReader() {
		return new InputStreamReader(this.getClass().getResourceAsStream(
				this.getClass().getSimpleName() + DEFAULT_ZUL_FILE_EXTENSION));
	}

}
