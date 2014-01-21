package com.jcrud.query;

public class QueryOperation implements QueryNode {

	private final QueryNode queryNode1;
	private final Operator operator;
	private final QueryNode queryNode2;

	public QueryOperation(QueryNode queryNode1, Operator operator, QueryNode queryNode2) {
		this.queryNode1 = queryNode1;
		this.operator = operator;
		this.queryNode2 = queryNode2;
	}

	@Override
	public String getSql() {

		return "(" + queryNode1.getSql() + " " + operator.getSymbol() + " " + queryNode2.getSql() + ")";
	}

	public QueryNode getQueryNode1() {
		return queryNode1;
	}

	public Operator getOperator() {
		return operator;
	}

	public QueryNode getQueryNode2() {
		return queryNode2;
	}

	@Override
	public boolean isOperation() {
		return true;
	}
}
