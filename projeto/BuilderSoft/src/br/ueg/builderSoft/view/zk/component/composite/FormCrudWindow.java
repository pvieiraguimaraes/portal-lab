package br.ueg.builderSoft.view.zk.component.composite;

import java.util.List;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.view.zk.composer.ComposerController;



public class FormCrudWindow extends Window implements IFormWindow {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2120942252712095445L;

	private ComposerController<?> composer;

	@Wire
	private Div divFields;


	private String titulo = "Manutenção";

	public FormCrudWindow() {
		Executions.createComponents("/WEB-INF/builderSoft/components/FormCrudWindow.zul", this, null);
		processRecursive(this,this);
	}
	
	private void processRecursive(Component comp, Object composer) {
		Selectors.wireComponents(comp, composer, false);
		Selectors.wireEventListeners(comp, composer);
		
		List<Component> compList = comp.getChildren();
		for (Component vComp : compList) {
			if (vComp instanceof Window) {
				processRecursive(vComp, composer);
			}
		}
	}	

	public void setComposer(ComposerController<?> composer) {
		this.composer = composer;
		
		setTitle(composer.getUseCase());
		
		if(composer.getForm()!=null)
			composer.getForm().setParent(divFields);
			// new AnnotateDataBinder(divForm).loadAll();

			composer.getComponent().setAttribute("formCrudWindow", this);
		
	}

	public ComposerController<?> getComposer() {
		return composer;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
