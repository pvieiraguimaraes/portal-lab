package br.ueg.builderSoft.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Image {

	String width() default "0";
	String height() default "0";
	
	boolean thumb() default false;
	String thumbWidth() default "0";
	String thumbHeight() default "0";
	
}
