package com.apimock.model.impl;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.apimock.dao.evaluators.SerializedEvaluatorEntity;
import com.apimock.model.MockRequest;
import com.apimock.model.MockRequestEvaluator;
import com.jcrud.model.HttpRequest;
import com.jcrud.utils.adapter.Adapt;
import com.jcrud.utils.adapter.AdaptField;

@Adapt(to = SerializedEvaluatorEntity.class)
public class SerializedEvaluator implements MockRequestEvaluator {

	private static final long serialVersionUID = 6151542652256677678L;

	@AdaptField(toName = "bytes", with = SerializingAdapter.class)
	private final MockRequestEvaluator evaluator;

	public SerializedEvaluator(MockRequestEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	public <T extends Serializable> MockRequestEvaluator getEvaluator() {
		return evaluator;
	}

	@Override
	public boolean evaluate(MockRequest mockRequest, HttpRequest request) {
		MockRequestEvaluator evaluator = getEvaluator();

		return evaluator.evaluate(mockRequest, request);
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 31);
		hashCodeBuilder.append(evaluator);

		int hashCode = hashCodeBuilder.toHashCode();

		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof SerializedEvaluator))
			return false;

		SerializedEvaluator serviceMatcher = (SerializedEvaluator) obj;
		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(evaluator, serviceMatcher.evaluator);

		return equalsBuilder.isEquals();
	}
}
