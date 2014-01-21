package com.jcrud.query;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {

	private final String rawSql;
	private final QueryNode queryNode;

	private final List<QueryNode> nodes = new ArrayList<QueryNode>();

	public QueryParser(String rawSql) {

		this.rawSql = rawSql;
		queryNode = parseQuery(rawSql);
	}

	public QueryOperation getQueryOperation() {
		return (QueryOperation) queryNode;
	}

	public String getSql() {
		return rawSql;
	}

	private QueryNode parseQuery(String query) {

		QueryNode queryNode = null;

		String subQuery = null;
		while (query.contains("(")) {

			subQuery = getSubQuery(query);
			query = query.replace("(" + subQuery + ")", "$");

			QueryNode node = parseQuery(subQuery);
			nodes.add(node);
		}
		String[] words = query.split(" ");

		if (words.length == 3) {
			int operatorIndex = words.length - 2;
			Operator operator = Operator.valueOfSql(words[operatorIndex]);

			QueryNode node2 = null;
			String node2Word = words[operatorIndex + 1];
			if (node2Word.equals("$")) {
				node2 = nodes.get(0);
				nodes.remove(0);
			} else {
				node2 = new QueryId(node2Word);
			}

			QueryNode node1 = null;
			String node1Word = words[operatorIndex - 1];
			if (node1Word.equals("$")) {
				node1 = nodes.get(0);
				nodes.remove(0);
			} else {
				node1 = new QueryId(node1Word);
			}

			queryNode = new QueryOperation(node1, operator, node2);

		} else if (words.length == 1) {
			queryNode = new QueryId(query);
		}
		if (queryNode != null) {
			return queryNode;
		}

		throw new IllegalStateException("Cannot parse query: " + query);
	}

	private String getSubQuery(String query) {

		int rightestToken = getRightestToken(query);

		for (int i = rightestToken + 1; i < query.length(); i++) {
			char charAtI = query.charAt(i);

			if (charAtI == ')') {
				return query.substring(rightestToken + 1, i);
			}
		}
		throw new IllegalStateException("Wrong number of parenthesis");
	}

	private int getRightestToken(String query) {

		for (int i = query.length() - 1; i >= 0; i--) {
			char charAtI = query.charAt(i);

			if (charAtI == '(') {
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) {

		String query = "j <> (a like (b or c))";

		QueryNode queryNode = new QueryParser(query).getQueryOperation();
		System.out.println(queryNode);
	}

}
