package com.jcrud.utils.adapter;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
public @interface Adapt {

	Class<?> to();

	Class<? extends ClassAdapter> with() default DefaultClassAdapter.class;
}
