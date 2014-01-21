package com.jcrud.query;

public enum Operator {

	AND("AND", true), OR("OR", true), LIKE("LIKE", false), EQUAL("=", false), NOT_EQUAL("<>", false), GREATER(">", false), LESS("<", false), GREATER_EQUAL(">=", false), LESS_EQUAL("<=", false), BETWEEN(
			"BETWEEN", false), IN("IN", false);

	private final String symbol;

	private final boolean isLogical;

	private Operator(String symbol, boolean isLogical) {
		this.symbol = symbol;
		this.isLogical = isLogical;
	}

	public String getSymbol() {
		return symbol;
	}

	public boolean isLogical() {
		return isLogical;
	}

	public static Operator valueOfSql(String sql) {
		Operator[] values = Operator.values();
		for (Operator value : values) {
			if (value.getSymbol().equalsIgnoreCase(sql)) {
				return value;
			}
		}
		throw new IllegalArgumentException(String.format("Sql value '%s' does not match with any Operator", sql));
	}
}
