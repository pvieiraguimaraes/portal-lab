package br.ueg.portalLab.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation para as entidades.
 * Respons�vel por definir se o atributo (campo) da tabela 
 * � obrigat�rio ou n�o e se � um campo de busca.
 * @author Diego
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Attribute {

	boolean Required() default true;
	boolean SearchField();
	
}
