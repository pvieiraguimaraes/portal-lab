package br.ueg.builderSoft.view.managed;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.ueg.builderSoft.control.GenericControl;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.util.reflection.Reflection;

/**
 * Super Classe criado para permitir que o ManageBeam possa ser utilizado sem acomplamento com o tipo de Visão.
 * Foi testado esssa aplicação para integração com JSF e ZK
 * @author guiliano
 *
 * @param <E>
 */
public abstract class GenericMB<E extends Entity> implements IGenericMB<E> {

	protected GenericControl<E> control;
	@AttributeView(key = "id", isEntityValue = true, entityType = Long.class, isVisible = false, caption = "mb_idColumn")
	protected long fldId;
	protected List<E> listEntity;
	@AttributeView(key = "searchValue")
	protected String fldBusca = "";
	protected ListingControl<E> listingControl;
	@AttributeView(key = "selectedEntity")
	protected E selectedEntity;

	public GenericMB() {
		super();
	}

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

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#getFldId()
	 */
	@Override
	public long getFldId() {
		return fldId;
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#setFldId(long)
	 */
	@Override
	public void setFldId(long fldId) {
		this.fldId = fldId;
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#getFldBusca()
	 */
	@Override
	public String getFldBusca() {
		return fldBusca;
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#setFldBusca(java.lang.String)
	 */
	@Override
	public void setFldBusca(String fldBusca) {
		this.fldBusca = fldBusca;
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#getListEntity()
	 */
	@Override
	public List<E> getListEntity() {
		return listEntity;
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#setListEntity(java.util.List)
	 */
	@Override
	public void setListEntity(List<E> listEntity) {
		this.listEntity = listEntity;
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#setListingControl(br.ueg.builderSoft.util.control.ListingControl)
	 */
	@Override
	public void setListingControl(ListingControl<E> listingControl) {
		this.listingControl = listingControl;
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#getListingControl()
	 */
	@Override
	public ListingControl<E> getListingControl() {
		return listingControl;
	}
	//Fim dos getters and Setters

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#getSelectedEntity()
	 */
	@Override
	public E getSelectedEntity() {
		return selectedEntity;
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#setSelectedEntity(E)
	 */
	@Override
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
			}
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#isListing()
	 */
	@Override
	public boolean isListing() {
		return listingControl.doAction(null, null);
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#getListColumns()
	 */
	@Override
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
						String vEntityType = Reflection.getClassName(field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).entityType());
						boolean vIsVisible = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).isVisible();
						int vComponent = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).component();
						if (field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).isEntityValue()) {
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

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.managed.IGenericMB#getEntityClass()
	 */
	@Override
	@SuppressWarnings("rawtypes")
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

}