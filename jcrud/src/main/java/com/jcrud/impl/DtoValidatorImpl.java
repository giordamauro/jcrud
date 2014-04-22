package com.jcrud.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.jcrud.model.DtoValidator;
import com.jcrud.model.exceptions.CRUDValidationException;

public class DtoValidatorImpl implements DtoValidator{

	private static final Validator validator;
	
	static {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		 validator = factory.getValidator();
	}
	
	@Override
	public void validate(Object dtoObject) throws CRUDValidationException{
		
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(dtoObject);

		if(!constraintViolations.isEmpty()){
			
			String messages = "";
			
			for(ConstraintViolation<Object> violation : constraintViolations){
				messages += String.format("Violation on property '%s.%s' : '%s'\n", violation.getRootBeanClass().getName(), violation.getPropertyPath(), violation.getMessage());
			}
			
			messages = messages.substring(0, messages.length() - 1);
		
			throw new CRUDValidationException(dtoObject.getClass(), messages);
		}
	}

}
