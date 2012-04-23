package br.ueg.builderSoft.view.zk.composer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.GenericControl;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
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

	protected GenericControl<E> genericControl;
	
	@Autowired
	protected Control<E> control;
	
	@AttributeView(key = "id", isEntityValue = true, fieldType = Long.class, isVisible = false, caption = "mb_idColumn")
	protected Long fldId;
	
	protected List<E> listEntity;
	
	@AttributeView(key = "searchValue")
	private String fldBusca = "%";
	
	protected ListingControl<E> listingControl;
	
	@AttributeView(key = "selectedEntity")
	protected E selectedEntity;
	
	protected String useCase;
	
	//atributos para componetiza��o
	
	@Wire
	protected Listhead listhead;
	
	@Wire
	protected Listitem listitem;

	protected Div searchForm;

	protected Div result;

	protected Div form;


	
	
	/**
	 * usado para pegar chamar o zk para passar os parametros da vis�o para o composer
	 */
	protected AnnotateDataBinder binder;
	protected Component component;
	
	@SuppressWarnings("unchecked")
	public ComposerController(){
		
		MessagesWebZK pMessages = new MessagesWebZK();
		Control<E> newControl = this.getNewControl(pMessages);
		listingControl = new ListingControl<E>();
		
		/*if(newControl==null){
			genericControl = new GenericControl<E>(pMessages, listingControl, this);
		}else{*/
			genericControl = new GenericControl<E>(pMessages, listingControl, this, newControl);
		//}
		this.setControl(genericControl.getControl());

		try {
			this.setSelectedEntity((E) getEntityClass().newInstance());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//initializeEntity();
	}
	
	/**Metodo utilizado para instanciar o controlador que sera utilizado
	 * Caso ele return null Ser� utilizado o controlador padr�o de crud(br.ueg.builderSoft.control.Control)
	 * @param genericControl TODO
	 * @return
	 */
	public abstract Control<E> getNewControl(MessagesControl pMessagesControl);
	
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
		//TODO depois s� pode haver um(avaliar quest�o de desempenho antes da remo��o, s� remover se tiver problema de desempenho)
		comp.setAttribute(comp.getId() + "Controller", this, true);
		comp.setAttribute("controller", this, true);
		
		
		this.component = comp;
		
		this.genericControl.setControl(this.getControl());
		
		binder = new AnnotateDataBinder(component);
		binder.setLoadOnSave(false);
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
	 * @param genericControl TODO
	 * @return o controlador
	 */
	protected GenericControl<E> getGenericControl() {
		return genericControl;
	}

	/**
	 * seta o controlador
	 * @param control
	 */
	protected void setGenericControl(GenericControl<E> control) {
		this.genericControl = control;
	}

	/**
	 * @return the fldId
	 */
	public Long getFldId() {
		return fldId;
	}

	/**
	 * @param fldId the fldId to set
	 */
	public void setFldId(Long fldId) {
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
	 * M�todo que verifica no ListingControl se foi feito uma listagem e atualiza/seta
	 * a lista da vis�o
	 */
	protected void verifyListing(String action) {
		
			if (this.isListing()) {
				listEntity = listingControl.getList();
				//binder.loadAll();
			}
	}

	/**
	 * M�todo que verifica se j� h� uma listagem ou n�o
	 * @return true se h� uma listagem, ou false caso contr�rio
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
							boolean existCaption = ((MessagesControl) this.getGenericControl().getController(MessagesControl.class)).existsMessage(vEntityCaption);
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
	 * TODO n�o sei se precisa das anota��es daqui para baixo mas n�o retirei ainda. 
	 */
	
	
	//TODO mudan�a ZK
		//@Command("select")
		//@NotifyChange({ "selectedEntity", "vm" })	
		public void selectEntity(){
			if(this.getSelectedEntity()!=null)
				this.getGenericControl().associateEntityToAttributeView(this.getSelectedEntity());
		}

		/**
		 * M�todo respons�vel por receber a "action" dos bot�es, chamando o controle para 
		 * execut�-la
		 * @param ActionEvent
		 */
		//@Command
		//@NotifyChange({ "listEntity", "vm" })
		public boolean doAction(@BindingParam("action") String action) {
			boolean result = false;
			binder.saveAll();	
			if (genericControl.doAction(action, initializeEntity())) {
				verifyListing(action);
				hideEditForm();	
				result = true;
					
			}
			binder.loadAll();
			return result;
		}
		
		
		
		/**
		 * M�todo para os bot�es que inicia uma nova vari�vel
		 * @param event
		 */
		//@Command
		//@NotifyChange({ "selectedEntity", "vm", "viewFormEdit" })
		public void newEntity() {
			this.setSelectedEntity(this.initializeEntity());
			this.genericControl.associateEntityToAttributeView(this.getSelectedEntity());
			binder.loadAll();
			binder.saveAll();
			this.showEditForm();
		}
		
		//@Command
		//@NotifyChange({ "selectedEntity", "vm", "viewFormEdit" })
		public void cancelEditEntity() {
			this.genericControl.doAction("cancelEdit", getSelectedEntity());
			this.genericControl.associateEntityToAttributeView(this.initializeEntity());
			this.hideEditForm();
		}
		

		
		/**
		 * M�todo para cancelar uma a��o
		 * @param event
		 */
		//@Command
		public void cancelAction() {
			this.genericControl.associateEntityToAttributeView(this.initializeEntity());
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
		
		public void associateEntityToView(E entity){ 
			this.genericControl.associateEntityToAttributeView(entity);
		}
		
		public boolean searchEntity(){
			return this.doAction("SEARCH");
		}
		
		public boolean saveEntity(){
			if(this.doAction("SAVE")){
				this.hideEditForm();
				return true;
			}
			return false;
		}
		
		public  boolean editEntity(){
			binder.saveAll();
			
			//this.doAction("ASSOCIATE");
			this.genericControl.associateEntityToAttributeView(this.getSelectedEntity());
			binder.loadComponent(this.getEditForm());
			//TODO descobrir uma forma de n�o fazer isso(ler tudo, deveria funcionar s� com o comando acima, 
			//quando o formul�rio � construido automaticamente.
			binder.loadAll();
			//binder.saveAll();
			this.showEditForm();
			return true;
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
		
		protected void processRecursive(Component comp, Object composer) {
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
			((MessagesControl) this.getGenericControl().getController(MessagesControl.class)).addMessage(message, MessagesType.INFO);
		}
		
		public BindingListModelList<Entity> getEntityModel(List<Entity> list){
			
			BindingListModelList<Entity> listEntityModel;
			if(list!=null && !list.isEmpty()){
				Collections.sort( list, new Comparator<Entity>(){
				    public int compare( Entity e1, Entity e2 ) {
				      return e1.compare(e2);
				    }
				  });
			 }else{
				 list = new ArrayList<Entity>();
			 }

			listEntityModel = new BindingListModelList<Entity>(list,true);
			return listEntityModel;
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
				 Control<E> control2 = this.getGenericControl().getControl();
				 List<Entity> listFKEntity2=null;
				 if(control2!=null){
					 listFKEntity2 = control2.getListFKEntity(fkEntity);
				 }
				 if(listFKEntity2!=null && !listFKEntity2.isEmpty()){
					 listFKEntity = (ArrayList<Entity>) listFKEntity2;
				 }else{
					 listFKEntity = new ArrayList<Entity>();
				 }
				
				Collections.sort( listFKEntity);
			}else{
				listFKEntity = new ArrayList<Entity>();
			}
		
			categoriaUsuarioModel = new BindingListModelList<Entity>(listFKEntity,true);
			
			
			return categoriaUsuarioModel;
		}

		/**
		 * @return the control
		 */
		public Control<E> getControl() {
			return control;
		}

		/**
		 * @param control the control to set
		 */
		public void setControl(Control<E> control) {
			this.control = control;
		}
}
