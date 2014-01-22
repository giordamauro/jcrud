package com.jcrud.utils.query;

import java.lang.reflect.Field;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.jcrud.query.Operator;
import com.jcrud.query.QueryBinaryOp;
import com.jcrud.query.QueryId;
import com.jcrud.query.QueryNode;
import com.jcrud.query.QueryOperation;
import com.jcrud.query.QueryParser;
import com.jcrud.query.QueryTernaryOp;

public final class QueryUtil {

	private QueryUtil() {
	}

	public static Criterion getCriterionForQuery(String querySql, Class<?> resourceClass) {

		QueryOperation queryOp = getQueryOperation(querySql);
		Criterion criterion = getQueryCritierion(queryOp, resourceClass);

		return criterion;
	}

	public static QueryOperation getQueryOperation(String querySql) {

		QueryOperation queryOp = new QueryParser(querySql).getQueryOperation();
		return queryOp;
	}

	public static Criterion getQueryCritierion(QueryOperation queryOp, Class<?> resourceClass) {
		QueryNode queryNode1 = null;
		QueryNode queryNode2 = null;

		Operator operator = queryOp.getOperator();

		if (queryOp.isTernary()) {

			QueryTernaryOp ternaryOp = (QueryTernaryOp) queryOp;

			queryNode1 = ternaryOp.getQueryNode1();
			queryNode2 = ternaryOp.getQueryNode2();
		} else {
			QueryBinaryOp binaryOp = (QueryBinaryOp) queryOp;
			queryNode1 = binaryOp.getQueryNode();
		}

		if (operator.getType() == Operator.Type.LOGICAL) {

			Criterion criterionNode1 = null;
			if (queryNode1.isOperation()) {
				QueryOperation queryOpNode1 = (QueryOperation) queryNode1;
				criterionNode1 = getQueryCritierion(queryOpNode1, resourceClass);
			}

			if (queryOp.isTernary()) {

				Criterion criterionNode2 = null;
				if (queryNode2.isOperation()) {
					QueryOperation queryOpNode2 = (QueryOperation) queryNode2;
					criterionNode2 = getQueryCritierion(queryOpNode2, resourceClass);
				}

				if (operator == Operator.AND) {
					return Restrictions.and(criterionNode1, criterionNode2);
				} else if (operator == Operator.OR) {
					return Restrictions.or(criterionNode1, criterionNode2);
				}
			} else if (operator == Operator.NOT) {
				return Restrictions.not(criterionNode1);
			}

			throw new IllegalStateException(String.format("Invalid logical operator '%s'", operator));

		} else if (operator.getType() == Operator.Type.ARITHMETICAL) {

			if (queryOp.isTernary()) {

				if (operator == Operator.BETWEEN) {

					QueryId propertyNode = (QueryId) queryNode1;
					String propertyName = propertyNode.getText();

					QueryTernaryOp betweenOpNode = (QueryTernaryOp) queryNode2;
					Operator betweenOperator = betweenOpNode.getOperator();
					QueryId lo = (QueryId) betweenOpNode.getQueryNode1();
					QueryId hi = (QueryId) betweenOpNode.getQueryNode2();

					if (betweenOperator == Operator.AND) {
						Object loValue = getCastedToFieldType(resourceClass, propertyName, lo.getText());
						Object hiValue = getCastedToFieldType(resourceClass, propertyName, hi.getText());

						return Restrictions.between(propertyName, loValue, hiValue);
					}
				} else {

					QueryId id1 = (QueryId) queryNode1;
					QueryId id2 = (QueryId) queryNode2;

					String propertyName = id1.getText();
					Object value = getCastedToFieldType(resourceClass, propertyName, id2.getText());

					if (operator == Operator.EQUAL) {
						return Restrictions.eq(propertyName, value);
					} else if (operator == Operator.GREATER) {
						return Restrictions.gt(propertyName, value);
					} else if (operator == Operator.GREATER_EQUAL) {
						return Restrictions.ge(propertyName, value);
					} else if (operator == Operator.LESS) {
						return Restrictions.lt(propertyName, value);
					} else if (operator == Operator.LESS_EQUAL) {
						return Restrictions.gt(propertyName, value);
					} else if (operator == Operator.GREATER) {
						return Restrictions.le(propertyName, value);
					} else if (operator == Operator.LIKE) {
						return Restrictions.like(propertyName, value);
					} else if (operator == Operator.NOT_EQUAL) {
						return Restrictions.ne(propertyName, value);
					}
				}
			}
		}
		throw new IllegalStateException(String.format("Invalid arithmetical operator '%s'", operator));
	}

	private static Object getCastedToFieldType(Class<?> resourceClass, String field, String value) {

		try {
			Field resourceField = resourceClass.getDeclaredField(field);
			Class<?> fieldType = resourceField.getType();

			Object fieldValue = null;
			// TODO: completar otros tipos
			if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
				fieldValue = Long.valueOf(value);
			} else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
				fieldValue = Integer.valueOf(value);
			} else {
				fieldValue = value;
			}

			return fieldValue;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
