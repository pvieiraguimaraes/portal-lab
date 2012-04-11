package br.ueg.builderSoft.control;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import javax.persistence.JoinColumn;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.control.IListingControl;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.reflection.Reflection;
import br.ueg.builderSoft.view.managed.IGenericMB;

/**
 * Controlador principal, onde contém a lista de outros controladores
 * e faz as ações por reflection
 * @author Diego
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class GenericControl <E extends Entity> {

	private Control<E> control;
//	private MessagesControl messages;
//	private ValidatorControl validator;
//	private ListingControl<E> listingControl;
	private SubControllerManager subControllerManager;
	private IGenericMB<E> view;
	
	public GenericControl(MessagesControl pMessages, ListingControl<E> pListing, IGenericMB<E> pView) {	
		
		this.initGenericControl(pMessages, pListing, pView, new Control<E>(pMessages));
		
	}
	
	private void initGenericControl(MessagesControl pMessages, ListingControl<E> pListing, IGenericMB<E> pView, Control<E> pControl){
		
		this.subControllerManager = new SubControllerManager<E>();
		this.subControllerManager.addController(pListing);
		this.subControllerManager.addController(pMessages);
		
		for(SubController v: pControl.getListValidator()){
			this.subControllerManager.addController(v);
		}
		this.view = pView;
		this.control = pControl;
		
	}

	public GenericControl(MessagesControl pMessages, ListingControl<E> pListing, IGenericMB<E> pView, Control<E> pControl) {	
		
		this.initGenericControl(pMessages, pListing, pView, pControl);	

	}
	
	/**
	 * Método que seta o controlador (Spring ainda não utilizado)
	 * @param control
	 */
	public void setControl(Control<E> control) {
		this.control = control;
	}
	
	public Control<E> getControl() {
		return control;
	}

	
	
	/**
	 * Adiciona um subControlador a lista dos mesmos
	 * @param object
	 */
	public void addController(SubController object) {
		this.subControllerManager.addController(object);
	}
	public SubController getController(Class pClass) {
		return this.subControllerManager.getController(pClass);
	}
	
	
	

	/**
	 * Método que faz qualquer ação vinda da visão por reflection
	 * @param action, String que representa o ID do componente
	 * @return se ação foi executada ou não
	 */
	public final boolean doAction(String action, E entity) {
		boolean result = false;
		this.mapManagerBeanToEntity(entity, this.view);
		HashMap<String, Object> map = this.createMapFields(entity);
		this.control.setMapFields(map);
		if (control.doAnyAction(this.subControllerManager, action)) {
			Class reflectedClass = control.getClass();
			try {
				Method method = reflectedClass.getMethod("action" + action.substring(0, 1).toUpperCase() + action.substring(1).toLowerCase(), SubControllerManager.class);
				result = (Boolean) method.invoke(control, this.subControllerManager);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} 
		if (result) {
			if (!action.equalsIgnoreCase("ASSOCIATE"))
				this.subControllerManager.getMessagesControl().doAction(null, "success");
			if (!action.equalsIgnoreCase("LIST") && !action.equalsIgnoreCase("SEARCH")) {
				map = new HashMap<String, Object>();
				IListingControl<E> listingControl = (IListingControl<E>) this.subControllerManager.getListingControl();
				map.put("searchValue", listingControl.getSearchValue());
				map.put("entity", entity);
				this.control.setMapFields(map);
				if (listingControl.isSearch()) {
					this.control.actionSearch(this.subControllerManager);
				} else 
					this.control.actionList(this.subControllerManager);
			}
		} else {
			this.subControllerManager.getMessagesControl().doAction(null, "cancel");
			result = false;
		}
		return result;
	}
	
	/**
	 * Metodo para receber a entidade e mapear com os dados do managed bean
	 * @author Guiliano
	 * @param entity
	 * @param vMB
	 */
	public void mapManagerBeanToEntity(E entity, IGenericMB<E> vMB){ 
		
		for (Class<?> reflectedClass = vMB.getClass(); reflectedClass != null; reflectedClass = reflectedClass.getSuperclass()) {
			Field[] fields = reflectedClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class) != null) {
					try {
						Object fieldValue = Reflection.getFieldValue(vMB, field.getName());
						String fieldName = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).key();
						Class fieldType = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).fieldType();
						if (field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).isEntityValue()) {
							Reflection.setFieldValue(entity, fieldName, fieldValue, fieldType);
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * Método responsável por analisar a visão e trazer os campos que tem a annotation
	 * AttributeView e os envia para o control	 
	 * @param entity
	 * @return HashMap com todos atributes que contém a annotation
	 */
	private HashMap<String, Object> createMapFields(E entity) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (Class<?> reflectedClass = this.view.getClass(); reflectedClass != null; reflectedClass = reflectedClass.getSuperclass()) {
			Field[] fields = reflectedClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class) != null) {
					try {
						Object fieldValue = Reflection.getFieldValue(this.view, field.getName());
						String fieldName = field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).key();
						if (!field.getAnnotation(br.ueg.builderSoft.util.annotation.AttributeView.class).isEntityValue()) {
							map.put(fieldName, fieldValue);
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		map.put("entity", entity);
		return map;
	}
	
	/**
	 * Método que recebe uma Entidade com parâmetros setados (ou um novo) e seta cada
	 * campo a seu respectivo representante na visão.
	 * @param E extends Entity, entidade
	 * @return true se conseguiu setar todos campos, false caso contrario
	 */
	public boolean associateEntityToAttributeView(E selectedEntity) {
		boolean result = true;
		for (Class<?> reflectedEntity = selectedEntity.getClass(); reflectedEntity != null; reflectedEntity = reflectedEntity.getSuperclass()) {
			Field[] fields = reflectedEntity.getDeclaredFields();
			for (Field field : fields) {
				Class fieldType = field.getType();
				String fieldName = field.getName();
				try {
					Object fieldValue = Reflection.getFieldValue(selectedEntity, fieldName);
					Class reflectedView = this.view.getClass();
					String methodName = "setFld" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					Method methods[] = reflectedView.getMethods();
					boolean exists = false;
					for(Method tempMethod :Arrays.asList(methods)){
						if( tempMethod.getName().equals(methodName) ) {
							exists = true;
							break;
						}
					}
					if(exists){
						Method method = reflectedView.getMethod(methodName, fieldType);
						method.invoke(this.view, fieldValue);
					}
				} catch (SecurityException e) {
					result = false;
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					result = false;
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					result = false;
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					result = false;
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					result = false;
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	//TODO analisar e fazer direito
	public boolean listFK(Entity entity) {
		boolean result = false;
		Class reflectedClass = entity.getClass();
		Field[] fields = reflectedClass.getDeclaredFields();
		for (Field actual : fields) {
			JoinColumn columnJoined = actual.getAnnotation(JoinColumn.class);
			if (columnJoined != null) {
				result = true;
				control.listFK(actual.getClass(), subControllerManager); 
			}
		}
		return result;
	}

	/**
	 * @return the subControllerManager
	 */
	public SubControllerManager getSubControllerManager() {
		return subControllerManager;
	}

	/**
	 * @param subControllerManager the subControllerManager to set
	 */
	public void setSubControllerManager(SubControllerManager subControllerManager) {
		this.subControllerManager = subControllerManager;
	}

}