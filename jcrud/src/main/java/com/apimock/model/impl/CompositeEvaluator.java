package com.apimock.model.impl;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.apimock.dao.evaluators.CompositeEvaluatorEntity;
import com.apimock.model.MockRequest;
import com.apimock.model.MockRequestEvaluator;
import com.jcrud.model.HttpRequest;
import com.jcrud.utils.adapter.Adapt;

@Adapt(to = CompositeEvaluatorEntity.class)
public class CompositeEvaluator implements MockRequestEvaluator {

	private static final long serialVersionUID = 5627270734179748634L;

	private final MockRequestEvaluator evaluator1;

	private final MockRequestEvaluator evaluator2;

	public CompositeEvaluator(MockRequestEvaluator evaluator1, MockRequestEvaluator evaluator2) {
		this.evaluator1 = evaluator1;
		this.evaluator2 = evaluator2;
	}

	@Override
	public boolean evaluate(MockRequest mockRequest, HttpRequest request) {
		return evaluator1.evaluate(mockRequest, request) && evaluator2.evaluate(mockRequest, request);
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 31);
		hashCodeBuilder.append(evaluator1);
		hashCodeBuilder.append(evaluator2);

		int hashCode = hashCodeBuilder.toHashCode();

		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof CompositeEvaluator))
			return false;

		CompositeEvaluator serviceMatcher = (CompositeEvaluator) obj;
		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(evaluator1, serviceMatcher.evaluator1);
		equalsBuilder.append(evaluator2, serviceMatcher.evaluator2);

		return equalsBuilder.isEquals();
	}
}
