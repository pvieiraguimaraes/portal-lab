package br.ueg.builderSoft.util.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Procurar um nome melhoar para essa classe
 * @author Guiliano
 *
 */
public class Reflection {
	/**
	 * Método que executa o método get*Field* por reflection, devolvendo o valor do campo.
	 * @param entity
	 * @param fieldName
	 * @return o valor associado ao campo.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getFieldValue(Object entity, String fieldName) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class entityClass = entity.getClass();
		Method method = entityClass.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));		
		Object retorno = method.invoke(entity);
		return retorno;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Class getFieldClass(Object entity, String fieldName)throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class entityClass = entity.getClass();
		Method method = entityClass.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));		
		return method.getReturnType();		
	}
	/**
	 * Método que executa o método get*Field* por reflection.
	 * sobrecarga do metodo setFieldValue(Object entity, String fieldName, Object fieldValue, Class paramType)
	 * @param entity
	 * @param fieldName
	 * @param fieldValue
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void setFieldValue(Object entity, String fieldName, Object fieldValue) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {		
		Reflection.setFieldValue(entity, fieldName, fieldValue, fieldValue.getClass());
	}
	/**
	 * M�todo que executa o m�todo get*Field* por reflection.
	 * @param entity
	 * @param fieldName
	 * @param fieldValue
	 * @param paramType
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setFieldValue(Object entity, String fieldName, Object fieldValue, Class paramType) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		Class entityClass = entity.getClass();
		Method method = entityClass.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), paramType);
		method.invoke(entity, fieldValue);	
	}
	
	
	// returns the class (without the package if any)
	  @SuppressWarnings("rawtypes")
	public static String getClassName(Class c) {
	    String FQClassName = c.getName();
	    int firstChar;
	    firstChar = FQClassName.lastIndexOf ('.') + 1;
	    if ( firstChar > 0 ) {
	      FQClassName = FQClassName.substring ( firstChar );
	      }
	    return FQClassName;
	}

}
