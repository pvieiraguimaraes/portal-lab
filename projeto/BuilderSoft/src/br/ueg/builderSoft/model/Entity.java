package br.ueg.builderSoft.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.builderSoft.util.reflection.Reflection;

/**
 * Classe genérica de entidade Tem apenas o método que difere de todas as outras
 * entidades (ID) e reescreve os métodos de comparação usando o Id como
 * atributo.
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "serial", "rawtypes" })
public abstract class Entity implements Serializable, Comparable {

	/**
	 * Método que retorna a chave primária
	 * 
	 * @return a PK
	 */
	public abstract Long getId();
	
	/**
	 * Método que configurar a chave primária
	 * 
	 * @author guiliano 
	 * 
	 */
	public abstract void setId(Long id);


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (getId() != other.getId())
			return false;
		return true;
	}

	/**
	 * Método que retorna a lista de possíveis campos de pesquisa com o nome no
	 * banco de dados
	 * 
	 * @return List<String>
	 */
	public List<String> getSearchColumnTable(Entity entity) {
		List<String> columnTable = new ArrayList<String>();
		for (Class<?> reflectedClass = entity.getClass(); reflectedClass != null; reflectedClass = reflectedClass.getSuperclass()) {
			Field[] fields = reflectedClass.getDeclaredFields();
			for (Field field : fields) {
				try {
					if (field.getAnnotation(Attribute.class).SearchField()) {
						columnTable.add(field.getAnnotation(Column.class).name());
					}
				} catch (Exception e) {	}
			}
		}
			
		return columnTable;
	}

	/**
	 * Método que retorna a lista de possíveis campos de pesquisa com o nome do
	 * atributo da entidade
	 * 
	 * @return List<String>
	 */
	public List<String> getSearchColumnEntity(Entity entity) {
		List<String> columnEntity = new ArrayList<String>();
		for (Class<?> reflectedClass = entity.getClass(); reflectedClass != null; reflectedClass = reflectedClass.getSuperclass()) {
			Field[] fields = reflectedClass.getDeclaredFields();
			for (Field field : fields) {
				try {
					if (field.getAnnotation(Attribute.class).SearchField()) {
						columnEntity.add(field.getName());
					}
				} catch (Exception e) {	}
			}
		}
			
		return columnEntity;
	}
	
	public String getName(){
		return Reflection.getClassName(this.getClass());
	}
	
	/**
	 * Faz a compração entre duas entidades retorna 0 se for igual
	 * <0 se a entidade passada for menor
	 * >0 se a entidade passada for maior 
	 * @param e Entity -> entidade para ser comparada
	 * @return 9999 se ocorreu algum erro
	 * @deprecated
	 */
	public int compare(Entity e){
		if (this == e)
			return 0;
		if (e == null)
			return 9999;
		if (getClass() != e.getClass()) 
			return 9999;
		Entity other = (Entity) e;
		return this.getColumnCompare().compareToIgnoreCase(other.getColumnCompare());
	}
	public int compareTo(Object e){
		if (this == e)
			return 0;
		if (e == null)
			return 9999;
		if (getClass() != e.getClass()) 
			return 9999;
		
		Entity other = (Entity) e;
		return this.getColumnCompare().compareToIgnoreCase(other.getColumnCompare());		
	}
	
	public boolean isNew(){
		return this.getId()!=null;
	}
	
	/**@deprecated
	 * @return retorna a string utilizada para comprarar as entidades para efeito de ordenação.
	 */
	protected String getColumnCompare(){
		return String.valueOf(getId());
	}
}
