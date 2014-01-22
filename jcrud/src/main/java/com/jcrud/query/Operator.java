package com.jcrud.query;

public enum Operator {

	AND(Type.LOGICAL), OR(Type.LOGICAL), NOT(Type.LOGICAL), LIKE(Type.ARITHMETICAL), EQUAL("=", Type.ARITHMETICAL), NOT_EQUAL("<>", Type.ARITHMETICAL), GREATER(">", Type.ARITHMETICAL), LESS("<",
			Type.ARITHMETICAL), GREATER_EQUAL(">=", Type.ARITHMETICAL), LESS_EQUAL("<=", Type.ARITHMETICAL), BETWEEN(Type.ARITHMETICAL);

	public static enum Type {
		ARITHMETICAL, LOGICAL, FUNCTION
	}

	private final String symbol;

	private final Type type;

	private Operator(String symbol, Type type) {
		this.symbol = symbol;
		this.type = type;
	}

	private Operator(Type type) {
		this.symbol = name();
		this.type = type;
	}

	public String getSymbol() {
		return symbol;
	}

	public Type getType() {
		return type;
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
