package br.ueg.builderSoft.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import br.ueg.builderSoft.model.Entity;

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
	
	/** M�todo que seta o valor do atributo em null
	 * @param entity
	 * @param fieldName
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void setFieldNull(Object entity, String fieldName) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Reflection.setFieldValue(entity, fieldName, null, Reflection.getFieldClass(entity, fieldName));
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
	  /**
		 * Retorna o {@link Type} representando o parâmetro Generics da classe na posição solicitada
		 * 
		 * @param clazz classe com Generics
		 * @param parameterIndex índice do parâmetro Generics
		 * @return {@link Type} Parametrizado no índice solicitado
	 * @throws Exception 
		 */
		public static Type getParameterizedType(Class<?> clazz, int parameterIndex) throws Exception {
			Type genericSuperClass = clazz.getGenericSuperclass();
			Class<?> clazz2 = clazz;
			while (!(genericSuperClass instanceof ParameterizedType)) {
				clazz2 = clazz2.getSuperclass();
				genericSuperClass = clazz2.getGenericSuperclass();
			}
			ParameterizedType classParameter = (ParameterizedType) genericSuperClass;
			try {
				return classParameter.getActualTypeArguments()[parameterIndex];
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new Exception("Verify you Class implementation of " + clazz.getName() + " there isn't a Generics in index " + parameterIndex + ", implement one!", e);
			}
		}
		
		/**
		 * Retorna o parâmetro Generics da classe na posição solicitada já convertido em classe
		 * 
		 * @param clazz classe com Generics
		 * @param parameterIndex índice do parâmetro Generics
		 * @return classe do Tipo Parametrizado
		 * @throws Exception 
		 */
		public static Class<?> getParameterizedTypeClass(Class<?> clazz, int parameterIndex) throws Exception {
			Type parameterizedType = getParameterizedType(clazz, parameterIndex);
			if (parameterizedType instanceof TypeVariable) {
				throw new IllegalArgumentException("It is not possible to convert into Class the class parameter ".concat(clazz.getName()));
			}
			return (Class<?>) parameterizedType;
		}
		
		/**
		 * Retorna uma lista de Fields que contém a anotação especificada.
		 * 
		 * @param entity entidade a ser percorrida
		 * @param step Annotation a ser procurada
		 * @return Lista de Fields
		 * @author Fernando
		 */
		public static List<Field> getAnnotatedFields(Entity entity, Class<? extends Annotation> step) {
			ArrayList<Field> resultList = new ArrayList<Field>();
			for (Field field : getFields(entity.getClass())) {
				if (field.isAnnotationPresent(step)) {
					resultList.add(field);
				}
			}
			return resultList;
		}
		
		/**
		 * Retorna uma lista de Fields de uma classe.
		 * 
		 * @param entity entidade extraída os Fields
		 * @return Array de Fields
		 * @author Fernando
		 */
		public static Field[] getFields(Class<?> classEntity) {
			Field[] fields = classEntity.getDeclaredFields();
			Class<?> superclass = classEntity.getSuperclass();
			while (superclass != null) {
				fields = ArrayUtils.addAll(fields, superclass.getDeclaredFields());
				superclass = superclass.getSuperclass();
			}
			return fields;
		}
}
