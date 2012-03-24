package br.ueg.builderSoft.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.builderSoft.util.reflection.Reflection;

/**
 * Classe gen�rica de entidade Tem apenas o m�todo que difere de todas as outras
 * entidades (ID) e reescreve os m�todos de compara��o usando o Id como
 * atributo.
 * 
 * @author Diego
 * 
 */
@SuppressWarnings("serial")
public abstract class Entity implements Serializable {

	/**
	 * M�todo que retorna a chave prim�ria
	 * 
	 * @return a PK
	 */
	public abstract long getId();
	
	/**
	 * M�todo que configurar a chave prim�ria
	 * 
	 * @author guiliano 
	 * 
	 */
	public abstract void setId(long id);
	
	public void setId(Long id){
		this.setId(id.longValue());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Long.signum(getId() ^ (getId() >>> 32));
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
	 * M�todo que retorna a lista de poss�veis campos de pesquisa com o nome no
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
	 * M�todo que retorna a lista de poss�veis campos de pesquisa com o nome do
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
}
