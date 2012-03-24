package br.ueg.portalLab.view.managed;

/**
 * @author Guiliano
 * Classe utilizada para obeter a listagem de campos do magedbean para ser utilizado no compoente de formulároCRUD
 */
public class ManagedBeanField {
	private String key;
	private String fieldName;
	private String fieldCaption;
	private String entityType;
	private boolean isVisible;
	private int component;
	
	public ManagedBeanField(String key, String fieldName, String fieldCaption, String entityType, boolean isVisible, int component){
		this.key = key;
		this.fieldName = fieldName;
		this.fieldCaption = fieldCaption;
		this.entityType = entityType;
		this.isVisible = isVisible;
		this.component = component;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public String getFieldCaption() {
		return fieldCaption;
	}
	public String getEntityType() {
		return this.entityType;
	}
	public boolean isVisible() {
		return isVisible;
	}

	public int getComponent() {
		return component;
	}

}
