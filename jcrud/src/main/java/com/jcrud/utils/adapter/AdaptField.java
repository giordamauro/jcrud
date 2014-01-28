package com.jcrud.utils.adapter;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
public @interface AdaptField {

	String toName() default "";

	Class<?> as() default AdaptField.class;

	Class<? extends FieldAdapter> with() default DefaultFieldAdapter.class;
}
