package com.jcrud.query;

public class QueryBinaryOp implements QueryOperation {

	private final QueryNode queryNode;
	private final Operator operator;

	public QueryBinaryOp(Operator operator, QueryNode queryNode) {
		this.queryNode = queryNode;
		this.operator = operator;
	}

	@Override
	public String getSql() {

		return "(" + operator.getSymbol() + " " + queryNode.getSql() + ")";
	}

	public QueryNode getQueryNode() {
		return queryNode;
	}

	public Operator getOperator() {
		return operator;
	}

	@Override
	public boolean isTernary() {
		return false;
	}

	@Override
	public boolean isOperation() {
		return true;
	}
}
