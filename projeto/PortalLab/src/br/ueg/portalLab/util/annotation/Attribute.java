package br.ueg.portalLab.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation para as entidades.
 * Responsável por definir se o atributo (campo) da tabela 
 * é obrigatório ou não e se é um campo de busca.
 * @author Diego
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Attribute {

	boolean Required() default true;
	boolean SearchField();
	
}
