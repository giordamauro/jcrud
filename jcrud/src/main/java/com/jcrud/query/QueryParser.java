package com.jcrud.query;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {

	private final List<Integer> leftTokens = new ArrayList<Integer>();
	private final List<Integer> rightTokens = new ArrayList<Integer>();

	private final String rawSql;

	private final QueryNode queryNode;

	public QueryParser(String rawSql) {

		this.rawSql = rawSql;
		findQueryTokens();

		queryNode = parseQuery(0, rawSql);
	}

	private void findQueryTokens() {

		for (int i = 0; i < rawSql.length(); i++) {
			char charAtI = rawSql.charAt(i);

			if (charAtI == '(') {
				leftTokens.add(i);
			} else if (charAtI == ')') {
				rightTokens.add(i);
			}
		}

		if (leftTokens.size() != rightTokens.size()) {
			throw new IllegalStateException("Wrong number of parenthesis in the query");
		}
	}

	private QueryNode parseQuery(int leftIndex, String query) {

		QueryNode queryNode = null;

		String subQuery = null;
		if (leftIndex < leftTokens.size()) {
			int leftmosttLeft = leftTokens.get(leftIndex);
			int rightestRight = rightTokens.get(rightTokens.size() - leftIndex - 1);

			subQuery = rawSql.substring(leftmosttLeft + 1, rightestRight);
		}

		if (subQuery != null) {
			query = query.replace("(" + subQuery + ")", "$");
		}
		if (query.contains(" ")) {
			String[] words = query.split(" ");
			// TODO: extender a otras operaciones (no solo de 3 elementos)
			if (words.length == 3) {
				Operator operator = Operator.valueOfSql(words[1]);

				String node1Word = words[0];
				QueryNode node1 = (node1Word.equals("$")) ? parseQuery(leftIndex + 1, subQuery) : new QueryId(node1Word);

				String node2Word = words[2];
				QueryNode node2 = (node2Word.equals("$")) ? parseQuery(leftIndex + 1, subQuery) : new QueryId(node2Word);

				queryNode = new QueryOperation(node1, operator, node2);

			} else {
				throw new UnsupportedOperationException("not done yet for: " + query);
			}
		} else {
			queryNode = new QueryId(query);
		}

		return queryNode;
	}

	public QueryOperation getQueryOperation() {
		return (QueryOperation) queryNode;
	}

	public static void main(String[] args) {

		String query = "j <> (a like ((b OR c) and cd))";

		QueryNode queryNode = new QueryParser(query).getQueryOperation();
		System.out.println(queryNode);
	}

}
