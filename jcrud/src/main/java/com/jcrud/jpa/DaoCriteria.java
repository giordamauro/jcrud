package com.jcrud.jpa;

import java.util.List;

public interface DaoCriteria<T>{
	
	Class<T> getResourceClass();
	
	List<T> getElements(GenericDao genericDao, int offset, int limit);
}
