package br.ueg.builderSoft.view.zk.component.composite;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.view.managed.ManagedBeanField;
import br.ueg.builderSoft.view.zk.composer.ComposerController;



public class FormCrudWindow extends Window implements IFormWindow {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2120942252712095445L;

	private ComposerController<?> composer;

	@Wire
	private Div divFields;
	
	@SuppressWarnings("unused")
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
		
		if(composer.getForm()!=null){
			composer.getForm().setParent(divFields);
		}else{
			buildDivFieldsFromEntity(composer);
		}

			composer.getComponent().setAttribute("formCrudWindow", this);
			
		this.formCrudWindowBtnSave.setVisible(this.isCanSave()); 
	}
	
	private Grid g=null;
	private Div d  = null;
	private AnnotateDataBinder binder=null;
	private void buildDivFieldsFromEntity(ComposerController<?> composer) {
//		AnnotateDataBinder binder = new AnnotateDataBinder(crudFormWindow);
//		binder.bindBean("controller2", composer);
		
		if(divFields.getChildren().size()==0) {
			List<Component> list =  new ArrayList<Component>(divFields.getChildren());
			for(Component c : list){
				c.detach();
			}
			d = new Div();
			g = new Grid();
			d.appendChild(g);
			
			binder = new AnnotateDataBinder(g);
			binder.bindBean("controller2", composer);
				Columns c = new Columns();
					Column col1 = new Column();col1.setWidth("120px");
					Column col2 = new Column();col2.setAlign("left");
					c.appendChild(col1);
					c.appendChild(col2);
				Rows r = new Rows();
				Textbox t;
				Checkbox checkbox;
				for (ManagedBeanField field : composer.getListColumns()) {
					if (field.isVisible()) {
						Row row = new Row();					
						row.appendChild(new Label(Labels.getLabel(field.getFieldCaption())));
						String fieldName = "controller2."+field.getFieldName()+"";
						
						if(field.getFieldType().equalsIgnoreCase("boolean")){
							checkbox = new Checkbox();
							binder.addBinding(checkbox,"checked", fieldName);
							row.appendChild(checkbox);
							
						}else{
							t = new Textbox();
							
							t.setWidth("98%");
							
							binder.addBinding(t, "value", fieldName );
							row.appendChild(t);
						}
						r.appendChild(row);
					}
				}
				g.appendChild(r);
				
				d.setParent(this.divFields);
				//binder.loadAll();
		}
			binder.loadComponent(g);
			//binder.saveAll();
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
