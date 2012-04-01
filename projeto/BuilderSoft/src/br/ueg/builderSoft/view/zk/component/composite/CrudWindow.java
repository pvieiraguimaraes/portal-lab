package br.ueg.builderSoft.view.zk.component.composite;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.view.zk.composer.ComposerController;





public class CrudWindow extends Window implements IFormWindow{

	private static final long serialVersionUID = -3275412603663733915L;

	private ComposerController<?> composer;

	@Wire
	private Div divSearch;

	@Wire
	private Div divResult;

	@Wire
	private Div divForm;
	
	
	@Wire
	private Window crudWindow;
	
	@Wire
	private FormCrudWindow editFormCrudWindow;
	
	
	@Wire
	private Listhead divListhead;
	
	@Wire
	private Listitem divListitem;

	private String titulo = "Manutenção";

	public CrudWindow() {
		Component comp = Executions.createComponents("/WEB-INF/builderSoft/components/CrudWindow.zul", this, null);
		//Selectors.wireComponents(this, this, false);
		//Selectors.wireEventListeners(this, this);
		
		processRecursive(comp,this);
		
		
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
	private void setComposerRecursive(Component comp, Object composer){
		if(comp instanceof IFormWindow)
			((IFormWindow) comp).setComposer((ComposerController<?>) composer);
		List<Component> compList = comp.getChildren();
		for(Component vComp : compList ){
				setComposerRecursive(vComp, composer);
		}
	}

	public void setComposer(ComposerController<?> composer) {
		this.composer = composer;
		if(this.getChildren()!=null){
			setComposerRecursive(this.getChildren().get(0), composer);
		}
		
		composer.setEditForm(this.editFormCrudWindow);
		composer.setUseCase(getTitulo());

		
		if (composer.getSearchForm() != null) {
			composer.getSearchForm().setParent(divSearch);
			// new AnnotateDataBinder(divSearch).loadAll();
		}
		if(this.divResult!=null){
			composer.getResult().setParent(divResult);
			new AnnotateDataBinder(divResult).loadAll();
		}
		//Pega os campos configurados no template do componete para jogar na janela construida
		if(this.divListhead!=null && composer.getListhead()!=null){
			if(this.divListhead.getChildren().size()==1){
				Listheader oper = (Listheader) this.divListhead.getChildren().get(0);
				this.divListhead.removeChild(oper);
				
				List<Component> list =new ArrayList<Component>(composer.getListhead().getChildren());
				for(Component l : list ){
					Listheader h = (Listheader)l;
					this.divListhead.appendChild(h);
				}
				this.divListhead.appendChild(oper);
			}
			
		}		
		if(this.divListitem!=null && composer.getListitem()!=null){
			if(this.divListitem.getChildren().size()==1){
			Listcell cell = (Listcell) this.divListitem.getChildren().get(0);
			this.divListitem.removeChild(cell);			
			List<Component> list =new ArrayList<Component>(composer.getListitem().getChildren());
			for(Component l : list ){
				Listcell h = (Listcell)l;
				this.divListitem.appendChild(h);
			}
			this.divListitem.appendChild(cell);
			}
		}

			//composer.getForm().setParent(divForm);
			// new AnnotateDataBinder(divForm).loadAll();

			composer.getComponent().setAttribute("crudWindow", this);
		
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

	public Window getEditFormCrudWindow() {
		return editFormCrudWindow;
	}
	
	
}