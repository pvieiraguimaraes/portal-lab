package br.ueg.builderSoft.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation para as entidades.
 * Responsavel por definir se o atributo (campo) da tabela 
 * E obrigatario ou nao e se e um campo de busca.
 * @author Diego
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Attribute {

	boolean Required() default true;
	boolean SearchField();
	
}
