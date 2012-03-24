package br.ueg.portalLab.view.managed;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ueg.portalLab.control.TesteControl;
import br.ueg.portalLab.model.Reino;
import br.ueg.portalLab.util.annotation.AttributeView;

@ManagedBean
@SessionScoped
public class ReinoMB extends MB<Reino>{
	
	/*TODO GenericControl acessa o MB para indentificar os campos especificos do MB
	 * e trata ele/ e entrega ele para o control específico 
	 */
	
	@AttributeView(key = "reino", isEntityValue = true, entityType = String.class, isVisible=true, caption="reino_reinoColumn")
	private String fldReino;

	public ReinoMB() {
		super();
		this.control.setControl(new TesteControl<Reino>(this.control));
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

	@Override
	protected Reino initializeEntity() {
		return new Reino();
	}
	
	//TODO Fazer alterações no .xhtml

	/*@Override
	protected void initializeEntity() {
		setEntity(new Reino());
	}*/
	
	
}
