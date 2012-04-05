package br.ueg.portalLab.view.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ueg.builderSoft.view.managed.MB;
import br.ueg.portalLab.control.TesteControl;
import br.ueg.portalLab.model.Reino;
import br.ueg.builderSoft.util.annotation.AttributeView;

@ManagedBean
@SessionScoped
public class ReinoMB extends MB<Reino>{
	
	/*TODO GenericControl acessa o MB para indentificar os campos especificos do MB
	 * e trata ele/ e entrega ele para o control específico 
	 */
	
	@AttributeView(key = "reino", isEntityValue = true, fieldType = String.class, isVisible=true, caption="reino_reinoColumn")
	private String fldReino;

	public ReinoMB() {
		super();
		this.control.setControl(new TesteControl<Reino>(this.control));
		this.getControl().doAction("LIST", initializeEntity());
	}

	/**
	 * @return the fldReino
	 */
	public String getFldReino() {
		return fldReino;
	}

	/**
	 * @param fldReino the fldReino to set
	 */
	public void setFldReino(String fldReino) {
		this.fldReino = fldReino;
	}

	public Class getEntityClass() {
		return Reino.class;
	}
	
	//TODO Fazer alterações no .xhtml

	/*@Override
	protected void initializeEntity() {
		setEntity(new Reino());
	}*/
	
	
}
