package br.ueg.builderSoft.view.zk.component.composite;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
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
	
	private Component crudWindowComponent;

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
	
	@Wire
	private Button crudWindowBtnNew;
	
	@Wire
	private Button crudWindowBtnEdit;
	
	@Wire
	private Button crudWindowBtnDelete;

	private String titulo = "Manuten��o";
	
	/**
	 *  Atributo para indicar quais opera��es estaram dispon�vel no formul�rio de crud
	 *  Valor padr�o � "create,read,update,delete" ou seja todas as opera��es dispon�veis
	 */
	private String crudOperation = "create,read,update,delete";
	
	private boolean crudCreateEnable;
	
	private boolean crudReadEnable;
	
	private boolean crudUpdateEnable;
	
	private boolean crudDeleteEnable;
	
	

	public CrudWindow() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("crudWindow", this);
				
		crudWindowComponent = Executions.createComponents("/WEB-INF/builderSoft/components/CrudWindow.zul", this, params);		
		//Selectors.wireComponents(this, this, false);
		//Selectors.wireEventListeners(this, this);
		
		processRecursive(crudWindowComponent,this);
				
		
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
		initCrudWindow(composer);
		
	}

	/**
	 * Faz a inicializa��o do compoente de CrudWindow
	 * @param composer
	 */
	private void initCrudWindow(ComposerController<?> composer) {
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
		buildListHearder(composer);		
		buildListItem(composer);
		
		//inicializar as variaveis de controle de opera��o baseado no atributo crudOperation
		initCrudOpeartion();
		
		updateComponents();

			//composer.getForm().setParent(divForm);
			// new AnnotateDataBinder(divForm).loadAll();

			composer.getComponent().setAttribute("builderSoftCrudWindow", this);
	}

	/**
	 * Metodo utilizado para pegar as opera��es do crudOperation e configurar os bot�es
	 */
	private void updateComponents() {
		crudWindowBtnNew.setVisible(this.crudCreateEnable);
		crudWindowBtnEdit.setVisible(this.crudUpdateEnable);
		crudWindowBtnDelete.setVisible(this.crudDeleteEnable);
		crudWindow.setTitle(this.getTitulo());
	}

	/**
	 * Metodo utilizado para pegar a string crudOperation e inicializar 
	 * crudCreateEnable, crudReadEnable, crudUpdateEnable, crudDeleteEnable
	 */
	private void initCrudOpeartion() {
		//verifica se o crudOperation foi configurado corretamente para dar sequencia
		if(getCrudOperation()==null &&(getCrudOperation()!=null && getCrudOperation().length()==0))  return;
		
		this.crudCreateEnable	= getCrudOperation().toLowerCase().contains("create");
		this.crudReadEnable 	= getCrudOperation().toLowerCase().contains("read");
		this.crudUpdateEnable	= getCrudOperation().toLowerCase().contains("update");
		this.crudDeleteEnable	= getCrudOperation().toLowerCase().contains("delete");
		
		//crudWindowComponent.invalidate();
	}

	/**
	 * faz a constru��o dos itens(listitem) da janela, pegando o conteudo da div listitem 
	 * (que est� na defini��o da janela utilizando o componente crudWindow) e adiciona 
	 * na jenela gerada pelo componente
	 * @param composer
	 */
	private void buildListItem(ComposerController<?> composer) {
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
	}

	/**
	 * faz a constru��o do cabe�alho(listheader) da janela, pegando o conteudo da div listhead 
	 * (que est� na defini��o da janela utilizando o componente crudWindow) e adiciona 
	 * na jenela gerada pelo componente
	 * @param composer
	 */
	private void buildListHearder(ComposerController<?> composer) {
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

	/**	 * 
	 * @return the crudOperation , que � uma string separada por virgula que pode ter:
	 * create - para mostrar o bot�o de novo
	 * read - para mostrar partes de leitura
	 * update - para mostrar o bot�o de editar
	 * delete - para mostra o bot�o de remo��o
	 */
	public String getCrudOperation() {
		return crudOperation;
	}

	/**
	 * @param crudOperation the crudOperation to set
	 */
	public void setCrudOperation(String crudOperation) {
		this.crudOperation = crudOperation;
		initCrudOpeartion();
	}

	/**
	 * @return the crudCreateEnable
	 */
	public boolean isCrudCreateEnable() {
		return crudCreateEnable;
	}

	/**
	 * @param crudCreateEnable the crudCreateEnable to set
	 */
	public void setCrudCreateEnable(boolean crudCreateEnable) {
		this.crudCreateEnable = crudCreateEnable;		
	}

	/**
	 * @return the crudReadEnable
	 */
	public boolean isCrudReadEnable() {
		return crudReadEnable;
	}

	/**
	 * @param crudReadEnable the crudReadEnable to set
	 */
	public void setCrudReadEnable(boolean crudReadEnable) {
		this.crudReadEnable = crudReadEnable;
	}

	/**
	 * @return the crudUpdateEnable
	 */
	public boolean isCrudUpdateEnable() {
		return crudUpdateEnable;
	}

	/**
	 * @param crudUpdateEnable the crudUpdateEnable to set
	 */
	public void setCrudUpdateEnable(boolean crudUpdateEnable) {
		this.crudUpdateEnable = crudUpdateEnable;
	}

	/**
	 * @return the crudDeleteEnable
	 */
	public boolean isCrudDeleteEnable() {
		return crudDeleteEnable;
	}

	/**
	 * @param crudDeleteEnable the crudDeleteEnable to set
	 */
	public void setCrudDeleteEnable(boolean crudDeleteEnable) {
		this.crudDeleteEnable = crudDeleteEnable;
	}
	
	
}