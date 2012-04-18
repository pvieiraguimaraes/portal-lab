package br.ueg.builderSoft.view.managed;

/**
 * @author Guiliano
 * Classe utilizada para obeter a listagem de campos do magedbean para ser utilizado no compoente de formulároCRUD
 */
public class ManagedBeanField {
	private String key;
	private String fieldName;
	private String fieldCaption;
	private String fieldType;
	private boolean isVisible;
	private int component;
	
	public ManagedBeanField(String key, String fieldName, String fieldCaption, String fieldType, boolean isVisible, int component){
		this.key = key;
		this.fieldName = fieldName;
		this.fieldCaption = fieldCaption;
		this.fieldType = fieldType;
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
	public String getFieldType() {
		return this.fieldType;
	}
	public boolean isVisible() {
		return isVisible;
	}

	public int getComponent() {
		return component;
	}

}
