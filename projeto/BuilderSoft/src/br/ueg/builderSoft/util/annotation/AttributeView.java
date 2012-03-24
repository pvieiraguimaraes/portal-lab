package br.ueg.builderSoft.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation para as os campos da vis�o
 * Respons�vel por definir a chave do campo e se � um campo de entidade
 * @author Diego
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AttributeView {

	/**
	 * representa��o do valor, nome do atributo
	 * @return key
	 */
	String key();
	/**
	 * @return Se o valor representa um campo da entidade
	 */
	boolean isEntityValue() default false;
	
	/**
	 * @return se o atributo deve ser exibido(coluna primeiro uso)
	 */
	boolean isVisible() default true;
	
	/**
	 * @return chave utilizada para buscar o caption no bundle
	 */
	String caption() default  "";
	/**
	 * Tipo da entidade
	 * @return Class da entidade (int, String, etc)
	 */
	@SuppressWarnings("rawtypes")
	Class entityType() default Object.class;
	/**
	 * Tipo de componente que o representar� no formul�rio
	 * @return ComponentType (inteiro) do componente
	 */
	int component() default 0;
	/**
	 * Ordem de exibi��o da campo, o primeiro a ser exibido deve ser 0
	 * @return ordem do compomente na vis�o
	 */
	int order() default 0;
	
}
