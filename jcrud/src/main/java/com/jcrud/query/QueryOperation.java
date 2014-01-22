package com.jcrud.query;

public interface QueryOperation extends QueryNode {

	boolean isTernary();

	Operator getOperator();
}
