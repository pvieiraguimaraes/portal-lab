package br.ueg.builderSoft.view.zk.composer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.GenericControl;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.constant.ControllerType;
import br.ueg.builderSoft.util.constant.MessagesType;
import br.ueg.builderSoft.util.control.IListingControl;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.reflection.Reflection;
import br.ueg.builderSoft.view.control.MessagesWebZK;
import br.ueg.builderSoft.view.managed.IGenericMB;
import br.ueg.builderSoft.view.managed.ManagedBeanField;

@SuppressWarnings("rawtypes")
public abstract class ComposerController<E extends Entity> extends GenericForwardComposer implements IGenericMB<E> {

	protected GenericControl<E> control;
	
	@AttributeView(key = "id", isEntityValue = true, fieldType = Long.class, isVisible = false, caption = "mb_idColumn")
	protected long fldId;
	
	protected List<E> listEntity;
	
	@AttributeView(key = "searchValue")
	private String fldBusca = "%";
	
	protected ListingControl<E> listingControl;
	
	@AttributeView(key = "selectedEntity")
	protected E selectedEntity;
	
	protected String useCase;
	
	//atributos para componetização
	
	@Wire
	protected Listhead listhead;
	
	@Wire
	protected Listitem listitem;

	protected Div searchForm;

	protected Div result;

	protected Div form;


	
	
	/**
	 * usado para pegar chamar o zk para passar os parametros da visão para o composer
	 */
	private AnnotateDataBinder binder;
	private Component component;
	
	@SuppressWarnings("unchecked")
	public ComposerController(){
		control = new GenericControl<E>(new MessagesWebZK(), new ListingControl<E>(), this);
		listingControl = (ListingControl<E>) control.getController(IListingControl.class);
		try {
			selectedEntity = (E) getEntityClass().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//initializeEntity();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4027241834740478664L;
	
	private static ComposerController _this;
	
	public static ComposerController getComposerController()
	{
		return (_this);
	}
	
	/**
	 * GenericForwardComposer interface
	 */
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);
			processRecursive(comp,  this);
		//TODO depois só pode haver um(avaliar questão de desempenho antes da remoção, só remover se tiver problema de desempenho)
		comp.setAttribute(comp.getId() + "Controller", this, true);
		comp.setAttribute("controller", this, true);
		
		
		this.component = comp;
		
		binder = new AnnotateDataBinder(component);
		binder.loadAll();
	}
	
	/**
	 * Recurse through DOM and return the component with name if found
	 * 
	 * @param component
	 * @param name
	 * @return
	 */
	public static Component getFellow(Component component, String name)
	{
		List<Component> children = component.getChildren();
		for (Component comp : children)
		{
			if (comp.getId().equals(name))
			{
				// System.out.println("# UI # FormController::getFellow found "
				// + comp);
				return (comp);
			}
			if (comp.getChildren() != null)
			{
				Component _comp = getFellow(comp, name);
				if (_comp != null)
				{
					return (_comp);
				}
			}
		}
		return (null);
	}
	
	
	
	/*
	 * Parte do GenericMB
	 * 
	 */
	/**
	 * @return o controlador
	 */
	protected GenericControl<E> getControl() {
		return control;
	}

	/**
	 * seta o controlador
	 * @param control
	 */
	protected void setControl(GenericControl<E> control) {
		this.control = control;
	}

	/**
	 * @return the fldId
	 */
	public long getFldId() {
		return fldId;
	}

	/**
	 * @param fldId the fldId to set
	 */
	public void setFldId(long fldId) {
		this.fldId = fldId;
	}

	/**
	 * @return the fldBusca
	 */
	public String getFldBusca() {
		return fldBusca;
	}

	/**
	 * @param fldBusca the fldBusca to set
	 */
	public void setFldBusca(String fldBusca) {
		this.fldBusca = fldBusca;
	}

	public List<E> getListEntity() {
		return listEntity;
	}

	public void setListEntity(List<E> listEntity) {
		this.listEntity = listEntity;
	}

	/**
	 * @param listingControl the listingControl to set
	 */
	public void setListingControl(ListingControl<E> listingControl) {
		this.listingControl = listingControl;
	}

	/**
	 * @return the listingControl
	 */
	public ListingControl<E> getListingControl() {
		return listingControl;
	}
	//Fim dos getters and Setters

	/**
	 * @return the selectedEntity
	 */
	public E getSelectedEntity() {
		return selectedEntity;
	}

	/**
	 * @param selectedEntity the selectedEntity to set
	 */
	public void setSelectedEntity(E selectedEntity) {
		this.selectedEntity = selectedEntity;
	}

	/**
	 * Método que verifica no ListingControl se foi feito uma listagem e atualiza/seta
	 * a lista da visão
	 */
	protected void verifyListing(String action) {
		
			if (this.isListing()) {
				listEntity = listingControl.getList();
				binder.loadAll();
			}
	}

	/**
	 * Método que verifica se já há uma listagem ou não
	 * @return true se há uma listagem, ou false caso contrário
	 */
	public boolean isListing() {
		return listingControl.doAction(null, null);
	}

	public List<ManagedBeanField> getListColumns() {
		List<ManagedBeanField> list = new ArrayList<ManagedBeanField>();
		
		for (Class<?> reflectedClass = this.getClass(); reflectedClass != null; reflectedClass = reflectedClass.getSuperclass()) {
			Field[] fields = reflectedClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class) != null) {
					try {
						//Object fieldValue = Reflection.getFieldValue(this, field.getName());
						String vKey = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).key();
						String fieldName = field.getName();
						//Class fieldType = field.getAnnotation(br.ueg.portalLab.util.annotation.AttributeView.class).entityType();
						String vCaption = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).caption();						
						String vEntityType = Reflection.getClassName(field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).fieldType());
						boolean vIsVisible = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).isVisible();
						int vComponent = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).component();

						if (field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).isEntityValue()) {
							String vEntityCaption = Reflection.getClassName(getEntityClass()).toLowerCase().concat("_").concat(vKey).concat("Column"); 
							boolean existCaption = ((MessagesControl) this.getControl().getController(MessagesControl.class)).existsMessage(vEntityCaption);
							if(existCaption){
								 vCaption = vEntityCaption;
							}
							list.add(new ManagedBeanField(vKey, fieldName, vCaption,vEntityType, vIsVisible, vComponent));
						}
					
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					
					}
				}
			}
		}
		
		return list;
	}

	public abstract Class getEntityClass();

	@SuppressWarnings("unchecked")
	protected E initializeEntity() {
		E retorno = null;
		try {
			retorno =  (E) getEntityClass().newInstance();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}
	
	/*
	 * Metodos do MBZK 
	 * TODO não sei se precisa das anotações daqui para baixo mas não retirei ainda. 
	 */
	
	
	//TODO mudança ZK
		//@Command("select")
		//@NotifyChange({ "selectedEntity", "vm" })	
		public void selectEntity(){
			if(selectedEntity!=null)
				this.control.associateEntityToAttributeView(selectedEntity);
		}

		/**
		 * Método responsável por receber a "action" dos botões, chamando o controle para 
		 * executá-la
		 * @param ActionEvent
		 */
		//@Command
		//@NotifyChange({ "listEntity", "vm" })
		public void doAction(@BindingParam("action") String action) {
			binder.saveAll();	
			if (control.doAction(action, initializeEntity())) {
				verifyListing(action);
				hideEditForm();
			}
		}
		
		
		
		/**
		 * Método para os botões que inicia uma nova variável
		 * @param event
		 */
		//@Command
		//@NotifyChange({ "selectedEntity", "vm", "viewFormEdit" })
		public void newEntity() {
			this.selectedEntity =this.initializeEntity();
			this.control.associateEntityToAttributeView(this.selectedEntity);
			binder.loadAll();
			binder.saveAll();
			this.showEditForm();
		}
		
		//@Command
		//@NotifyChange({ "selectedEntity", "vm", "viewFormEdit" })
		public void cancelEditEntity() {
			this.control.associateEntityToAttributeView(this.initializeEntity());
			this.hideEditForm();
		}
		

		
		/**
		 * Método para cancelar uma ação
		 * @param event
		 */
		//@Command
		public void cancelAction() {
			this.control.associateEntityToAttributeView(this.initializeEntity());
			verifyListing("cancel");
		}
		
		public void showEditForm(){
			//String casoDeUso=Reflection.getClassName(this.getEntityClass()).toLowerCase();
			//Window win = (Window) Executions.createComponents("/"+casoDeUso+"/"+casoDeUso+"_form.zul", null, null);
			
			getEditForm().setMode(Window.MODAL);
			getEditForm().setVisible(true);
			getEditForm().doModal();
	        //win.doModal();
		}
		
		public Component getComponent() {
			return component;
		}

		public void setComponent(Component component) {
			this.component = component;
		}

		/**
		 * @return the binder
		 */
		public AnnotateDataBinder getBinder() {
			return binder;
		}

		/**
		 * @param binder the binder to set
		 */
		public void setBinder(AnnotateDataBinder binder) {
			this.binder = binder;
		}

		public void hideEditForm(){
			getEditForm().setVisible(false);
		}
		public abstract Window getEditForm();
		public abstract void setEditForm(Window form);
		
		//@Command
		//@NotifyChange({ "selectedEntity", "vm", "viewFormEdit" })
		public  void editEntity(){
			this.doAction("ASSOCIATE");
			binder.loadAll();
			//binder.saveAll();			
			this.showEditForm();		
		}

		public Div getSearchForm() {
			return searchForm;
		}

		public Div getResult() {
			return result;
		}

		public Div getForm() {
			return form;
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

		public String getUseCase() {
			return useCase;
		}

		public void setUseCase(String useCase) {
			this.useCase = useCase;
		}

		public Listhead getListhead() {
			return listhead;
		}

		public void setListhead(Listhead listhead) {
			this.listhead = listhead;
		}

		public Listitem getListitem() {
			return listitem;
		}

		public void setListitem(Listitem listitem) {
			this.listitem = listitem;
		}
		public void addMessage(String message){
			((MessagesControl) this.control.getController(MessagesControl.class)).addMessage(message, MessagesType.INFO);
		}
		
		/**
		 * @param field nome do atributo que contem a entidade extrangeira(chave estrangeir)
		 * @return
		 */
		public BindingListModelList<Entity> getFKEntityModel(String field) {
			

			Entity fkEntity=null;
			try {
				fkEntity = (Entity) Reflection.getFieldClass(this, field).newInstance();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BindingListModelList<Entity> categoriaUsuarioModel;
			ArrayList<Entity> listFKEntity;
			if(fkEntity!=null){
				 listFKEntity = (ArrayList<Entity>) this.control.getControl().getListFKEntity(fkEntity);
				
				Collections.sort( listFKEntity, new Comparator<Entity>(){
				    public int compare( Entity e1, Entity e2 ) {
				      return e1.compare(e2);
				    }
				  });
			}else{
				listFKEntity = new ArrayList<Entity>();
			}
		
			categoriaUsuarioModel = new BindingListModelList<Entity>(listFKEntity,true);
			
			
			return categoriaUsuarioModel;
		}
}
