package br.ueg.builderSoft.view.zk.component.composite;

import java.util.List;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
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
	
	@Wire
	private Window crudFormWindow;
	
	@Wire
	private Button formCrudWindowBtnSave;


	private String titulo = "Manutenção";
	
	private CrudWindow crudWindow;
	
	private boolean canSave;

	public FormCrudWindow() {
		Executions.createComponents("~./builderSoft/components/FormCrudWindow.zul", this, null);
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
		
		initFormCrudWindow(composer);
		
	}

	/**
	 * Faz a inicialização do compoente de FormCrudWindow
	 * @param composer
	 */
	private void initFormCrudWindow(ComposerController<?> composer) {
		if(this.crudWindow!=null)
			setTitle(this.crudWindow.getTitulo());
		
		if(composer.getForm()!=null)
			composer.getForm().setParent(divFields);

			composer.getComponent().setAttribute("formCrudWindow", this);
			
		this.formCrudWindowBtnSave.setVisible(this.isCanSave());
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

	/**
	 * @return the crudWindow
	 */
	public CrudWindow getCrudWindow() {
		return crudWindow;
	}

	/**
	 * @param crudWindow the crudWindow to set
	 */
	public void setCrudWindow(CrudWindow crudWindow) {
		this.crudWindow = crudWindow;
	}

	/**
	 * @return the canSave
	 */
	public boolean isCanSave() {
		return canSave;
	}

	/**
	 * @param canSave the canSave to set
	 */
	public void setCanSave(boolean canSave) {
		this.canSave = canSave;
	}
}
