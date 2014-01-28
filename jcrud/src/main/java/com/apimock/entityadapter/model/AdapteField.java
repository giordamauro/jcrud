package com.apimock.entityadapter.model;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
public @interface AdapteField {

	String toName() default "";

	Class<? extends ValueTypeAdapter> withClass() default VoidTypeAdapter.class;
}
