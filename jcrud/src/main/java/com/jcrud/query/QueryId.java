package com.jcrud.query;

public class QueryId implements QueryNode {

	private final String identifier;

	public QueryId(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String getSql() {
		return identifier;
	}

	@Override
	public boolean isOperation() {
		return false;
	}
}
