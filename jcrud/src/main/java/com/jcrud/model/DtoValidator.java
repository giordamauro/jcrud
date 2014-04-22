package com.jcrud.model;

import com.jcrud.model.exceptions.CRUDValidationException;

public interface DtoValidator {

	void validate(Object dtoObject) throws CRUDValidationException;
}
