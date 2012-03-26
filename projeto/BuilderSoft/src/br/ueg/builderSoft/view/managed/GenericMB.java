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
public abstract class GenericMB<E extends Entity> {

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
			}
	}

	/**
	 * Método que verifica se já há uma listagem ou não
	 * @return true se há uma listagem, ou false caso contrário
	 */
	public boolean isListing() {
		return listingControl.doAction(null, null);
	}

	/**
	 * Metódo que inicializa a entidade
	 */
	protected abstract E initializeEntity();

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

}