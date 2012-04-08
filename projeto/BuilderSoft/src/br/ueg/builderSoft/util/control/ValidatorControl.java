package br.ueg.builderSoft.util.control;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.builderSoft.util.reflection.Reflection;

/**
 * SubControlador que faz as valida��es
 * @author Diego
 *
 */
public class ValidatorControl implements SubController{
	
	protected MessagesControl messagesControl;
	/*
	 * Criar um arraylist de classes de valida��o, onde o mesmo
	 * pode ser adicionado pelo mb ou control
	 */
	
	public ValidatorControl(MessagesControl pMessagesControl) {
		this.messagesControl = pMessagesControl;
	}
	
	/**
	 * M�todo que valida a entidade buscando por campos n�o preenchidos
	 * Adiciona o campo n�o preenchido a lista de emptyFields
	 * @param entity
	 * @return true se tudo for v�lido e false caso haja algum campo n�o preenchido
	 */
	public boolean validate(Entity entity) {
		boolean isValide = true;
		String classeName =  Reflection.getClassName(entity.getClass()).toLowerCase();
		for (Class<?> reflectedClass = entity.getClass(); reflectedClass != null; reflectedClass = reflectedClass.getSuperclass()) {//adicionado para verificar com heran�a		
			Field[] fields = reflectedClass.getDeclaredFields();
			for (Field currentField:fields) {
				try {
					if (currentField.getAnnotation(Attribute.class).Required()) {
						Object fieldValue = Reflection.getFieldValue(entity, currentField.getName());
						String bundleErroColumn =classeName.concat("_").concat(currentField.getName());
						if(!messagesControl.existsErrorMessage(bundleErroColumn)){
							bundleErroColumn = Reflection.getClassName(reflectedClass).toLowerCase().concat("_").concat(currentField.getName());
						}
						if (fieldValue == null || fieldValue.equals("")) {
							isValide = false;
							messagesControl.addMessageError(bundleErroColumn);
						}
					}
				} catch (SecurityException e) {
					// do nothing
				} catch (IllegalArgumentException e) {
					// do nothing
				} catch (NoSuchMethodException e) {
					// do nothing
				} catch (IllegalAccessException e) {
					// do nothing
				} catch (InvocationTargetException e) {
					// do nothing
				} catch (NullPointerException e) {
					// do nothing
				}
			}
		}
		return isValide;
	}
	
	/**
	 * M�todo que valida a entidade buscando por algum campo para efetuar a busca.
	 * Adiciona o campo n�o preenchido (busca) a lista de emptyFields
	 * @param entity
	 * @return true se algum campo de busca foi preenchido ou false caso contr�rio
	 */
	@SuppressWarnings("rawtypes")
	public boolean validateSearch(Entity entity) {
		boolean hasValue = false;
		Class entityClass = entity.getClass();
		Field[] fields = entityClass.getDeclaredFields();
		for (Field currentField:fields) {
			try {
				Object fieldValue = Reflection.getFieldValue(entity, currentField.getName());
				if (fieldValue != null && !fieldValue.equals("")) {
					hasValue = true;
				}
			} catch (SecurityException e) {
				// do nothing
			} catch (IllegalArgumentException e) {
				// do nothing
			} catch (NoSuchMethodException e) {
				// do nothing
			} catch (IllegalAccessException e) {
				// do nothing
			} catch (InvocationTargetException e) {
				// do nothing
			} catch (NullPointerException e) {
				// do nothing
			}
		}
		if (!hasValue) {
			messagesControl.addMessageError("Busca");
		}
		return hasValue;
	}
	
	@Override
	public boolean doAction(List<Object> atributes, String action) {
		if (action.equalsIgnoreCase("SEARCH")) {
			return validateSearch((Entity) atributes.get(0));
		} else {
			return validate((Entity) atributes.get(0));
		}
	}

}
